package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.controller.TestController;
import demo.template.sb3_3template.dto.res.FinancialDictionaryDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Page<FinancialDictionaryDto> findByCondition(int page, int size) {

//        Pageable pageable = PageRequest.of(page - 1, size);
//
//        List<FinancialDictionary> content = queryFactory
//                .select(new QFinancialDictionaryDto(
//                        financialDictionary.id
//                        financialDictionary.word
//                ))
//                .from(financialDictionary)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy()
//                .fetch();
//
//        JPQLQuery<Long> countQuery = queryFactory
//                .select(financialDictionary.count())
//                .from(financialDictionary);
//
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
        return null;
    }

    @Override
    public void updateTest(String userId, List<TestController.TestDto.TTT> watchListDto) {

        List<Long> watchlistIdList = watchListDto.stream().map(TestController.TestDto.TTT::getWatchListId).toList();


        Map<Long, Integer> positions = watchListDto.stream()
                .filter(s -> s.getPosition() > 0)
                .collect(Collectors
                .toMap(TestController.TestDto.TTT::getWatchListId, TestController.TestDto.TTT::getPosition));


        Map<Long, String> stdDate = watchListDto.stream()
                .filter(s -> s.getStdDate() != null)
                .collect(Collectors
                .toMap(TestController.TestDto.TTT::getWatchListId, TestController.TestDto.TTT::getStdDate));

        CaseBuilder.Cases<Integer, NumberExpression<Integer>> positionWhenCase = new CaseBuilder().when(Expressions.FALSE.isTrue()).then(watchlist.position);

        for (Map.Entry<Long, Integer> entry : positions.entrySet()) {
            positionWhenCase = positionWhenCase.when(watchlist.watchlistId.eq(entry.getKey())).then(entry.getValue());
        }
        NumberExpression<Integer> caseExpression = positionWhenCase.otherwise(0);

        CaseBuilder.Cases<String, StringExpression> stdDtWhenCase = new CaseBuilder().when(Expressions.FALSE.isTrue()).then(watchlist.standardDate);
        for (Map.Entry<Long, String> entry : stdDate.entrySet()) {
            stdDtWhenCase = stdDtWhenCase.when(watchlist.watchlistId.eq(entry.getKey())).then(entry.getValue());
        }
        StringExpression case2Expression = stdDtWhenCase.otherwise(watchlist.standardDate);

        queryFactory.update(watchlist)
                .set(watchlist.position, caseExpression)
                .set(watchlist.standardDate, case2Expression)
                .where(watchlist.userId.eq(userId)
                        .and(watchlist.watchlistId.in(watchlistIdList)))
                .execute();

    }

}
