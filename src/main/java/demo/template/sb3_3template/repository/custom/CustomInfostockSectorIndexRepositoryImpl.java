package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QRateOfReturnDto;
import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.dto.projection.*;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.enums.RankType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;
import static demo.template.sb3_3template.entity.mart.infostock.QInfostockSectorIndex.infostockSectorIndex;
import static demo.template.sb3_3template.entity.mart.infostock.QInfostockThemeStockMaster.infostockThemeStockMaster;


public class CustomInfostockSectorIndexRepositoryImpl implements CustomInfostockSectorIndexRepository{

    private final JPAQueryFactory queryFactory;

    public CustomInfostockSectorIndexRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RateOfReturnDto> findSectorRateOfReturn(List<Watchlist> sectorList, String targetDate) {

        return sectorList.stream().map(item -> queryFactory
                .select(new QRateOfReturnDto(
                        infostockSectorIndex.themeCd,
                        Expressions.cases()
                                .when(infostockSectorIndex.stdDt.eq(item.getStandardDate()))
                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                .otherwise(0).as("standardDateClose"),
                        Expressions.cases()
                                .when(infostockSectorIndex.stdDt.ne(item.getStandardDate()))
                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                .otherwise(0).as("targetDateClose"),
                        Expressions.numberTemplate(Integer.class, "({0} - {1}) / {1} * 100",
                                        infostockSectorIndex.close.castToNum(Integer.class), // targetDtClose
                                        // 타겟일자 종가
                                        Expressions.cases()
                                                .when(infostockSectorIndex.stdDt.eq(targetDate))
                                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                                .otherwise(0),
                                        // 기준일자 종가
                                        Expressions.cases()
                                                .when(infostockSectorIndex.stdDt.eq(item.getStandardDate()))
                                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                                .otherwise(0)
                                        .as("rateOfReturn")
                )))
                .from(infostockSectorIndex)
                .where(infostockSectorIndex.stdDt.in(item.getStandardDate(), targetDate))
                .fetchOne()).toList();
    }

    @Override
    public List<InfostockSectorIndex> findByThemeNmAndStdDtGoe(String sectorNm, String stdDt) {
        return queryFactory
                .select(infostockSectorIndex)
                .from(infostockSectorIndex)
                .where(infostockSectorIndex.themeNm.eq(sectorNm)
                        .and(infostockSectorIndex.stdDt.goe(stdDt)))
                .orderBy(infostockSectorIndex.stdDt.asc())
                .fetch();
    }

    @Override
    public Map<RankType, List<SectorReturn>> findSectorBy(String startDate, String endDate) {

        Map<RankType, List<SectorReturn>> sectorReturnMap = new HashMap<>();

        // 가장 가까운 시작일과 종료일을 가져오는 서브쿼리
        Tuple closestDates = queryFactory
                .select(infostockSectorIndex.stdDt.min(), infostockSectorIndex.stdDt.max())
                .from(infostockSectorIndex)
                .where(infostockSectorIndex.stdDt.between(startDate, endDate))
                .fetchOne();

        String closestStartDate = closestDates.get(infostockSectorIndex.stdDt.min());
        String closestEndDate = closestDates.get(infostockSectorIndex.stdDt.max());

        // 시작일과 종료일 데이터 (start_data, end_data) 동시에 가져오기
        List<Tuple> marketData = queryFactory
                .select(infostockSectorIndex.themeCd, infostockSectorIndex.themeNm, infostockSectorIndex.stdDt, infostockSectorIndex.close)
                .from(infostockSectorIndex)
                .where(infostockSectorIndex.stdDt.in(closestStartDate, closestEndDate))
                .fetch();

        Map<String, Tuple> startCloseMap = new HashMap<>();
        Map<String, Tuple> endCloseMap = new HashMap<>();

        for (Tuple tuple : marketData) {
            String stockCd = tuple.get(infostockSectorIndex.themeCd);
            String stdDt = tuple.get(infostockSectorIndex.stdDt);

            if (stdDt.equals(closestStartDate)) {
                startCloseMap.put(stockCd, tuple);
            } else if (stdDt.equals(closestEndDate)) {
                endCloseMap.put(stockCd, tuple);
            }
        }

        List<SectorReturn> result = new ArrayList<>();
        for (String key : startCloseMap.keySet()) {

            Tuple startTuple = startCloseMap.get(key);
            Tuple endTuple = endCloseMap.get(key);

            if (startTuple != null && endTuple != null) {
                BigDecimal startClose = new BigDecimal(startTuple.get(infostockSectorIndex.close));
                BigDecimal endClose = new BigDecimal(endTuple.get(infostockSectorIndex.close));
                BigDecimal returnRate = endClose.subtract(startClose).divide(startClose, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

                result.add(new SectorReturn(startTuple.get(infostockSectorIndex.themeCd), startTuple.get(infostockSectorIndex.themeNm), endTuple.get(infostockSectorIndex.stdDt), returnRate.doubleValue()));
            }
        }

        List<SectorReturn> sorted = result.stream().sorted(Comparator.comparing(SectorReturn::returnRate).reversed()).toList();
        List<SectorReturn> top5 = sorted.stream().limit(5).toList();
        List<SectorReturn> bottom5 = new ArrayList<>(sorted.stream().skip(Math.max(0, sorted.size() - 5)).toList());
        Collections.reverse(bottom5);

        sectorReturnMap.put(RankType.TOP, top5);
        sectorReturnMap.put(RankType.BOTTOM, bottom5);

        return sectorReturnMap;

    }

    @Override
    public List<StockThemeMkCap> findStockThemeByMkCap(List<String> stockCodeList) {

        BooleanBuilder whereClause = new BooleanBuilder();

        if (stockCodeList != null && !stockCodeList.isEmpty()) {
            whereClause.and(yhStockCode.stockCd.in(stockCodeList));
        }

        return queryFactory
                .select(new QStockThemeMkCap(
                        infostockThemeStockMaster.stockCd,
                        infostockThemeStockMaster.themeNm,
                        infostockSectorIndex.idxCalMkCap
                ))
                .from(infostockThemeStockMaster)
                .join(infostockSectorIndex)
                    .on(infostockThemeStockMaster.themeCd.eq(infostockSectorIndex.themeCd))
                .where(infostockSectorIndex.stdDt.eq(JPAExpressions.select(infostockSectorIndex.stdDt.max()).from(infostockSectorIndex)).and(whereClause))
                .fetch();
    }

}
