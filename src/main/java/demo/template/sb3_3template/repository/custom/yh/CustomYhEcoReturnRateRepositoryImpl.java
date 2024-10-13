package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;

import java.util.List;
import java.util.Optional;

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

}
