package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QRateOfReturnDto;
import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.entity.Watchlist;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QInfostockSectorIndex.infostockSectorIndex;
import static demo.template.sb3_3template.entity.mart.QYhEcoClose.yhEcoClose;
import static demo.template.sb3_3template.entity.mart.QYhEcoCode.yhEcoCode;
import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;

public class CustomYhEcoCodeRepositoryImpl implements CustomYhEcoCodeRepository {

    private final JPAQueryFactory queryFactory;

    public CustomYhEcoCodeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RateOfReturnDto> findIndexRateOfReturn(List<Watchlist> indexList, String targetDate, String dayType) {

        return indexList.stream().map(item -> queryFactory
                .select(new QRateOfReturnDto(
                        yhEcoCode.ecoCode,
                        Expressions.cases()
                                .when(yhEcoClose.stdDt.eq(item.getStandardDate()))
                                .then(yhEcoClose.ecoVal.castToNum(Integer.class))
                                .otherwise(0).as("standardDateClose"),
                        Expressions.cases()
                                .when(yhEcoClose.stdDt.ne(item.getStandardDate()))
                                .then(yhEcoClose.ecoVal.castToNum(Integer.class))
                                .otherwise(0).as("targetDateClose"),
                        Expressions.numberTemplate(Integer.class, "({0} - {1}) / {1} * 100",
                                yhEcoClose.ecoVal.castToNum(Integer.class), // targetDtClose
                                // 타겟일자 종가
                                Expressions.cases()
                                        .when(yhEcoClose.stdDt.eq(targetDate))
                                        .then(yhEcoClose.ecoVal.castToNum(Integer.class))
                                        .otherwise(0),
                                // 기준일자 종가
                                Expressions.cases()
                                        .when(yhEcoClose.stdDt.eq(item.getStandardDate()))
                                        .then(yhEcoClose.ecoVal.castToNum(Integer.class))
                                        .otherwise(0)
                                        .as("rateOfReturn")
                )))
                .from(yhEcoCode)
                .where(yhEcoCode.ecoCode.eq(item.getItemId()))
                .leftJoin(yhEcoClose.yhEcoCode, yhEcoCode)
                .where(yhEcoClose.stdDt.in(item.getStandardDate(), targetDate).and(yhEcoClose.dayType.eq(dayType)))
                .fetchOne()).toList();
    }

}
