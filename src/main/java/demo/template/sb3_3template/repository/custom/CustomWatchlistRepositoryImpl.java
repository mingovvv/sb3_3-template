package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.StockCompositeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static demo.template.sb3_3template.entity.QWatchlist.watchlist;

@Repository
public class CustomWatchlistRepositoryImpl implements CustomWatchlistRepository {

    private final JPAQueryFactory queryFactory;

    public CustomWatchlistRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void updateWatchlistPosition(Long watchlistId, String userId, String standardDate, int previousPosition, int targetPosition) {

        // Case 1: previousPosition < targetPosition
        if (previousPosition < targetPosition) {
            queryFactory.update(watchlist)
                    .set(watchlist.position, watchlist.position.subtract(1))
                    .where(watchlist.userId.eq(userId)
                            .and(watchlist.position.gt(previousPosition))
                            .and(watchlist.position.lt(targetPosition)))
                    .execute();
        }
        // Case 2: previousPosition > targetPosition
        else if (previousPosition > targetPosition) {
            queryFactory.update(watchlist)
                    .set(watchlist.position, watchlist.position.add(1))
                    .where(watchlist.userId.eq(userId)
                            .and(watchlist.position.gt(targetPosition))
                            .and(watchlist.position.lt(previousPosition)))
                    .execute();
        }

        JPAUpdateClause updateClause = queryFactory.update(watchlist)
                .set(watchlist.position, targetPosition)
                .set(watchlist.modifyId, userId)
                .set(watchlist.modifyDt, DateUtil.now())
                .where(watchlist.watchlistId.eq(watchlistId));

        if (standardDate != null) {
            updateClause.set(watchlist.standardDate, standardDate);
        }

        updateClause.execute();

    }

}
