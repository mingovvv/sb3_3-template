package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.projection.QStockMonthlyReturn;
import demo.template.sb3_3template.dto.projection.StockMonthlyReturn;
import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;
import static demo.template.sb3_3template.entity.mart.yh.QYhStockMReturnRate.yhStockMReturnRate;

public class CustomYhStockMReturnRateRepositoryImpl implements CustomYhStockMReturnRateRepository {

    private final JPAQueryFactory queryFactory;

    public CustomYhStockMReturnRateRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<YhStockMReturnRate> findByStockNmAndStockDtGoe(String stockNm, String stockDt) {
        return queryFactory
                .select(yhStockMReturnRate)
                .from(yhStockMReturnRate)
                .leftJoin(yhStockMReturnRate.yhStockCode, yhStockCode).fetchJoin()
                .where(yhStockMReturnRate.yhStockCode.stockNameKr.eq(stockNm)
                        .and(yhStockMReturnRate.stockDt.goe(stockDt)))
                .orderBy(yhStockMReturnRate.stockDt.asc())
                .fetch();
    }

    @Override
    public List<YhStockMReturnRate> findByStockDtGoe(String stockDt, String stdDt, String mkCapStart, String mkCapEnd) {
        return queryFactory
                .select(yhStockMReturnRate)
                .from(yhStockMReturnRate)
                .leftJoin(yhStockMReturnRate.yhStockCode, yhStockCode).fetchJoin()
                .leftJoin(yhMarket)
                    .on(yhStockMReturnRate.isin.eq(yhMarket.isin)
                            .and(yhStockMReturnRate.stockCd.eq(yhMarket.stockCd))
                            .and(yhMarket.stdDt.eq(stdDt)))
                .where(yhStockMReturnRate.stockDt.goe(stockDt)
                        .and(yhMarket.idxCalMkCap.between(mkCapStart, mkCapEnd)))
                .orderBy(yhStockMReturnRate.stockDt.asc())
                .fetch();
    }

    @Override
    public List<StockMonthlyReturn> findByStockDtGoe(String number, String number1) {
        return queryFactory
                .select(new QStockMonthlyReturn(
                        yhStockMReturnRate.stockCd,
                        yhStockCode.stockNameKr,
                        yhStockMReturnRate.stockDt,
                        yhStockMReturnRate.returnRateM,
                        yhMarket.idxCalMkCap
                ))
                .from(yhStockMReturnRate)
                .leftJoin(yhStockMReturnRate.yhStockCode, yhStockCode)
                .leftJoin(yhMarket)
                .on(yhStockMReturnRate.isin.eq(yhMarket.isin)
                        .and(yhStockMReturnRate.stockCd.eq(yhMarket.stockCd))
                        .and(yhMarket.stdDt.eq("20231101")))
                .where(yhStockMReturnRate.stockDt.goe(number))
                .fetch();
    }

}
