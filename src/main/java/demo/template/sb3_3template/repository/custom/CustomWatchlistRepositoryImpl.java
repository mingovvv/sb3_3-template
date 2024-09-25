package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.StockCompositeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomWatchlistRepositoryImpl implements CustomWatchlistRepository {

    private final JPAQueryFactory queryFactory;

    public CustomWatchlistRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

}
