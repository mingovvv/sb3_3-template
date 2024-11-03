package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.projection.IndexReturnCountry;
import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.entity.mart.YhEcoClose;
import demo.template.sb3_3template.enums.RankType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static demo.template.sb3_3template.entity.mart.QYhEcoClose.yhEcoClose;
import static demo.template.sb3_3template.entity.mart.QYhEcoCode.yhEcoCode;
import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;

public class CustomYhEcoCloseRepositoryImpl implements CustomYhEcoCloseRepository {

    private final JPAQueryFactory queryFactory;

    public CustomYhEcoCloseRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<YhEcoClose> findEcoNmAndStdDtGoe(String ecoNm, String stdDt, String dayType) {
        return queryFactory
                .select(yhEcoClose)
                .from(yhEcoClose.yhEcoCode, yhEcoCode).fetchJoin()
                .where(yhEcoClose.yhEcoCode.ecoNameKr.eq(ecoNm)
                        .and(yhEcoClose.dayType.eq(dayType))
                        .and(yhEcoClose.stdDt.goe(stdDt)))
                .orderBy(yhEcoClose.stdDt.asc())
                .fetch();
    }

    @Override
    public Map<RankType, List<IndexReturnCountry>> findEcoBy(String startDate, String endDate) {
        Map<RankType, List<IndexReturnCountry>> indexReturnMap = new HashMap<>();

        // 가장 가까운 시작일과 종료일을 가져오는 서브쿼리
        Tuple closestDates = queryFactory
                .select(yhEcoClose.stdDt.min(), yhEcoClose.stdDt.max())
                .from(yhEcoClose)
                .where(yhEcoClose.stdDt.between(startDate, endDate))
                .fetchOne();

        String closestStartDate = closestDates.get(yhEcoClose.stdDt.min());
        String closestEndDate = closestDates.get(yhEcoClose.stdDt.max());

        // 시작일과 종료일 데이터 (start_data, end_data) 동시에 가져오기
        List<Tuple> marketData = queryFactory
                .select(yhEcoClose.ecoCode, yhEcoCode.ecoNameKr, yhEcoClose.stdDt, yhEcoClose.ecoVal, yhEcoCode.countryKr)
                .from(yhEcoClose)
                .leftJoin(yhEcoClose.yhEcoCode, yhEcoCode)
                .where(yhEcoClose.stdDt.in(closestStartDate, closestEndDate))
                .fetch();

        Map<String, Tuple> startCloseMap = new HashMap<>();
        Map<String, Tuple> endCloseMap = new HashMap<>();

        for (Tuple tuple : marketData) {
            String ecoCd = tuple.get(yhEcoClose.ecoCode);
            String stdDt = tuple.get(yhEcoClose.stdDt);

            if (stdDt.equals(closestStartDate)) {
                startCloseMap.put(ecoCd, tuple);
            } else if (stdDt.equals(closestEndDate)) {
                endCloseMap.put(ecoCd, tuple);
            }
        }

        List<IndexReturnCountry> result = new ArrayList<>();
        for (String key : startCloseMap.keySet()) {

            Tuple startTuple = startCloseMap.get(key);
            Tuple endTuple = endCloseMap.get(key);

            if (startTuple != null && endTuple != null) {
                BigDecimal startClose = new BigDecimal(startTuple.get(yhEcoClose.ecoVal));
                BigDecimal endClose = new BigDecimal(endTuple.get(yhEcoClose.ecoVal));
                BigDecimal returnRate = endClose.subtract(startClose).divide(startClose, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

                result.add(new IndexReturnCountry(startTuple.get(yhEcoClose.ecoCode), startTuple.get(yhEcoCode.ecoNameKr), endTuple.get(yhEcoClose.stdDt), returnRate.doubleValue(), endTuple.get(yhEcoCode.countryKr)));
            }
        }

        List<IndexReturnCountry> sorted = result.stream().sorted(Comparator.comparing(IndexReturnCountry::returnRate).reversed()).toList();
        List<IndexReturnCountry> top5 = sorted.stream().limit(5).toList();
        List<IndexReturnCountry> bottom5 = new ArrayList<>(sorted.stream().skip(Math.max(0, sorted.size() - 5)).toList());
        Collections.reverse(bottom5);

        indexReturnMap.put(RankType.TOP, top5);
        indexReturnMap.put(RankType.BOTTOM, bottom5);

        return indexReturnMap;
    }

}
