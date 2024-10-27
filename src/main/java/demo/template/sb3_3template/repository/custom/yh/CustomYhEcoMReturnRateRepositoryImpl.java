package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.entity.mart.yh.YhEcoMReturnRate;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QYhEcoCode.yhEcoCode;
import static demo.template.sb3_3template.entity.mart.yh.QYhEcoMReturnRate.yhEcoMReturnRate;

public class CustomYhEcoMReturnRateRepositoryImpl implements CustomYhEcoMReturnRateRepository {

    private final JPAQueryFactory queryFactory;

    public CustomYhEcoMReturnRateRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<YhEcoMReturnRate> findEcoNmAndEcoDtGoe(String ecoNm, String ecoDt) {
        return queryFactory
                .select(yhEcoMReturnRate)
                .from(yhEcoMReturnRate.yhEcoCode, yhEcoCode).fetchJoin()
                .where(yhEcoMReturnRate.yhEcoCode.ecoNameKr.eq(ecoNm)
                        .and(yhEcoMReturnRate.ecoDt.goe(ecoDt)))
                .orderBy(yhEcoMReturnRate.ecoDt.asc())
                .fetch();
    }

}
