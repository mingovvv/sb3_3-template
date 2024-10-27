package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QRateOfReturnDto;
import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.infostock.QInfostockSectorIndex.infostockSectorIndex;


public class CustomInfostockSectorIndexRepositoryImpl implements CustomInfostockSectorIndexRepository{

    private final JPAQueryFactory queryFactory;

    public CustomInfostockSectorIndexRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RateOfReturnDto> findSectorRateOfReturn(List<Watchlist> sectorList, String targetDate) {

        return sectorList.stream().map(item -> queryFactory
                .select(new QRateOfReturnDto(
                        infostockSectorIndex.themeCd,
                        Expressions.cases()
                                .when(infostockSectorIndex.stdDt.eq(item.getStandardDate()))
                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                .otherwise(0).as("standardDateClose"),
                        Expressions.cases()
                                .when(infostockSectorIndex.stdDt.ne(item.getStandardDate()))
                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                .otherwise(0).as("targetDateClose"),
                        Expressions.numberTemplate(Integer.class, "({0} - {1}) / {1} * 100",
                                        infostockSectorIndex.close.castToNum(Integer.class), // targetDtClose
                                        // 타겟일자 종가
                                        Expressions.cases()
                                                .when(infostockSectorIndex.stdDt.eq(targetDate))
                                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                                .otherwise(0),
                                        // 기준일자 종가
                                        Expressions.cases()
                                                .when(infostockSectorIndex.stdDt.eq(item.getStandardDate()))
                                                .then(infostockSectorIndex.close.castToNum(Integer.class))
                                                .otherwise(0)
                                        .as("rateOfReturn")
                )))
                .from(infostockSectorIndex)
                .where(infostockSectorIndex.stdDt.in(item.getStandardDate(), targetDate))
                .fetchOne()).toList();
    }

    @Override
    public List<InfostockSectorIndex> findByThemeNmAndStdDtGoe(String sectorNm, String stdDt) {
        return queryFactory
                .select(infostockSectorIndex)
                .from(infostockSectorIndex)
                .where(infostockSectorIndex.themeNm.eq(sectorNm)
                        .and(infostockSectorIndex.stdDt.goe(stdDt)))
                .orderBy(infostockSectorIndex.stdDt.asc())
                .fetch();
    }

}
