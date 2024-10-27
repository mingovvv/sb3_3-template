package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.entity.mart.QYhEcoReturnRate;
import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.enums.BsnsDays;
import demo.template.sb3_3template.enums.RankType;

import java.util.*;

import static demo.template.sb3_3template.entity.mart.QYhEcoCode.yhEcoCode;
import static demo.template.sb3_3template.entity.mart.QYhEcoReturnRate.yhEcoReturnRate;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;
import static demo.template.sb3_3template.entity.mart.QYhStockReturnRate.yhStockReturnRate;

public class CustomYhEcoReturnRateRepositoryImpl implements CustomYhEcoReturnRateRepository {

    private final JPAQueryFactory queryFactory;

    public CustomYhEcoReturnRateRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<YhStockReturnRate> findTest(String stockCd, String isin, String stdDt, int bsnsDays) {
        return Optional.ofNullable(queryFactory
                .select(yhStockReturnRate)
                .from(yhStockReturnRate)
                .leftJoin(yhStockReturnRate.yhStockCode, yhStockCode)
                .where(yhStockReturnRate.yhStockCode.stockCd.eq(stockCd)
                        .and(yhStockReturnRate.yhStockCode.isin.eq(isin))
                        .and(yhStockReturnRate.stdDt.eq(stdDt))
                        .and(yhStockReturnRate.bsnsDays.eq(bsnsDays)))
                .fetchOne());
    }

    @Override
    public Map<RankType, List<YhEcoReturnRate>> findLastestIndicatorsByEcoTypeAndBsnsDays(String type, BsnsDays bsnsDays) {

        Map<RankType, List<YhEcoReturnRate>> ecoReturnMap = new HashMap<>();

        QYhEcoReturnRate subEcoReturnRate = new QYhEcoReturnRate("subEcoReturnRate");

        List<YhEcoReturnRate> ecoReturn = queryFactory
                .selectFrom(yhEcoReturnRate)
                .join(yhEcoReturnRate.yhEcoCode, yhEcoCode).fetchJoin()
                .where(yhEcoReturnRate.yhEcoCode.type.eq(type)
                        .and(yhEcoReturnRate.bsnsDays.eq(bsnsDays.getBsnsDays()))
                        .and(yhEcoReturnRate.stdDt.eq(
                                        JPAExpressions.select(subEcoReturnRate.stdDt.max())
                                                .from(subEcoReturnRate)
                                                .where(
                                                        subEcoReturnRate.yhEcoCode.ecoCode.eq(yhEcoReturnRate.yhEcoCode.ecoCode),
                                                        subEcoReturnRate.bsnsDays.eq(20)
                                                )
                                )
                        )).fetch();

        List<YhEcoReturnRate> top5 = ecoReturn.stream().sorted(Comparator.comparingDouble(YhEcoReturnRate::getReturnRate).reversed()).toList();
        List<YhEcoReturnRate> bottom5 = ecoReturn.stream().sorted(Comparator.comparingDouble(YhEcoReturnRate::getReturnRate)).toList();

        ecoReturnMap.put(RankType.TOP, top5);
        ecoReturnMap.put(RankType.BOTTOM, bottom5);

        return ecoReturnMap;
    }

}
