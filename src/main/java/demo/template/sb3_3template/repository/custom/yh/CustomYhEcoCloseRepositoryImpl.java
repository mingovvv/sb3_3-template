package demo.template.sb3_3template.repository.custom.yh;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.entity.mart.YhEcoClose;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QYhEcoClose.yhEcoClose;
import static demo.template.sb3_3template.entity.mart.QYhEcoCode.yhEcoCode;

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

}
