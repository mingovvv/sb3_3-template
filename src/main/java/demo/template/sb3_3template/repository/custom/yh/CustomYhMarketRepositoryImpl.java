package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.entity.mart.YhMarket;
import demo.template.sb3_3template.enums.RankType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;

public class CustomYhMarketRepositoryImpl implements CustomYhMarketRepository{

    private final JPAQueryFactory queryFactory;

    public CustomYhMarketRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void findTest() {
//        queryFactory
//                .select(yhMarket)
//                .from(yhMarket)
//                .leftJoin(yhMarket.yhStockCode, yhStockCode)
//                .on(yhMarket.yhStockCode.stockCd.eq(stockCd)
//                        .and(yhMarket.yhStockCode.isin.eq(isin))
//                        .and(yhMarket.stdDt.eq(standardDate)))
//                .
        return;
    }

    @Override
    public List<YhMarket> findByStockNmAndStdDtGoe(String stockNm, String stdDt) {
        return queryFactory
                .select(yhMarket)
                .from(yhMarket)
                .leftJoin(yhMarket.yhStockCode, yhStockCode).fetchJoin()
                .where(yhMarket.yhStockCode.stockNameKr.eq(stockNm)
                        .and(yhMarket.stdDt.goe(stdDt)))
                .fetch();
        }

    @Override
    public Map<RankType, List<StockReturnMkCap>> findStockBy(String startDate, String endDate) {

        Map<RankType, List<StockReturnMkCap>> stockReturnMap = new HashMap<>();

        // 가장 가까운 시작일과 종료일을 가져오는 서브쿼리
        Tuple closestDates = queryFactory
                .select(yhMarket.stdDt.min(), yhMarket.stdDt.max())
                .from(yhMarket)
                .where(yhMarket.stdDt.between(startDate, endDate))
                .fetchOne();

        String closestStartDate = closestDates.get(yhMarket.stdDt.min());
        String closestEndDate = closestDates.get(yhMarket.stdDt.max());

        // 시작일과 종료일 데이터 (start_data, end_data) 동시에 가져오기
        List<Tuple> marketData = queryFactory
                .select(yhMarket.stockCd, yhStockCode.stockNameKr, yhMarket.stdDt, yhMarket.close, yhMarket.idxCalMkCap)
                .from(yhMarket)
                .leftJoin(yhMarket.yhStockCode, yhStockCode)
                .where(yhMarket.stdDt.in(closestStartDate, closestEndDate))
                .fetch();

        Map<String, Tuple> startCloseMap = new HashMap<>();
        Map<String, Tuple> endCloseMap = new HashMap<>();

        for (Tuple tuple : marketData) {
            String stockCd = tuple.get(yhMarket.stockCd);
            String stdDt = tuple.get(yhMarket.stdDt);

            if (stdDt.equals(closestStartDate)) {
                startCloseMap.put(stockCd, tuple);
            } else if (stdDt.equals(closestEndDate)) {
                endCloseMap.put(stockCd, tuple);
            }
        }

        List<StockReturnMkCap> result = new ArrayList<>();
        for (String key : startCloseMap.keySet()) {

            Tuple startTuple = startCloseMap.get(key);
            Tuple endTuple = endCloseMap.get(key);

            if (startTuple != null && endTuple != null) {
                BigDecimal startClose = new BigDecimal(startTuple.get(yhMarket.close));
                BigDecimal endClose = new BigDecimal(endTuple.get(yhMarket.close));
                BigDecimal returnRate = endClose.subtract(startClose).divide(startClose, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

                result.add(new StockReturnMkCap(startTuple.get(yhMarket.stockCd), startTuple.get(yhStockCode.stockNameKr), endTuple.get(yhMarket.stdDt), returnRate.doubleValue(), endTuple.get(yhMarket.idxCalMkCap)));
            }
        }

        List<StockReturnMkCap> sorted = result.stream().sorted(Comparator.comparing(StockReturnMkCap::returnRate).reversed()).toList();
        List<StockReturnMkCap> top5 = sorted.stream().limit(5).toList();
        List<StockReturnMkCap> bottom5 = new ArrayList<>(sorted.stream().skip(Math.max(0, sorted.size() - 5)).toList());
        Collections.reverse(bottom5);

        stockReturnMap.put(RankType.TOP, top5);
        stockReturnMap.put(RankType.BOTTOM, bottom5);

        return stockReturnMap;
    }

}
