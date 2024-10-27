package demo.template.sb3_3template.repository.custom.fs;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class CustomFsRepositoryImpl implements CustomFsRepository {

    private final JPAQueryFactory queryFactory;

    public CustomFsRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

}
