package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.entity.Watchlist;

import java.util.List;

public class CustomYhEcoReturnRateRepositoryImpl implements CustomYhEcoReturnRateRepository {

    private final JPAQueryFactory queryFactory;

    public CustomYhEcoReturnRateRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RateOfReturnDto> findIndesRateOfReturn(List<Watchlist> indexList, String targetDate) {
        return null;
    }

}
