package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.jpa.impl.JPAQueryFactory;

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

}
