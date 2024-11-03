package demo.template.sb3_3template.repository.custom.fs;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.entity.fs.Fs;

import java.util.List;

import static demo.template.sb3_3template.entity.fs.QFs.fs;

public class CustomFsRepositoryImpl implements CustomFsRepository {

    private final JPAQueryFactory queryFactory;

    public CustomFsRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Fs> findFs(String year, String endYearOfPeriod) {

        BooleanExpression fsDtCondition = fs.fsDt.goe(year);

        if (endYearOfPeriod != null) {
            fsDtCondition = fsDtCondition.or(fs.fsDt.eq(endYearOfPeriod));
        }

        return queryFactory
                .select(fs)
                .from(fs)
                .where(fs.acctStd.eq("A")
                        .and(fs.cumType.eq("A"))
                        .and(fs.fsType.eq("A"))
                        .and(fs.quarter.eq("4"))
                        .and(fs.stockCd.eq("A"))
                        .and(fs.acctStd.eq("A"))
                        .and(fs.industry.eq("A"))
                        .and(fsDtCondition))
                .orderBy(fs.fsDt.desc())
                .fetch();
    }

}
