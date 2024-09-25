package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QRateOfReturnDto;
import demo.template.sb3_3template.dto.QStockCompositeDto;
import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.dto.StockCompositeDto;
import demo.template.sb3_3template.entity.Watchlist;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;
import static demo.template.sb3_3template.entity.raw.QInfostockTheme.infostockTheme;
import static demo.template.sb3_3template.entity.raw.QInfostockThemeStock.infostockThemeStock;

@Repository
public class CustomYhStockCodeRepositoryImpl implements CustomYhStockCodeRepository {

    private final JPAQueryFactory queryFactory;

    public CustomYhStockCodeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<StockCompositeDto> findStockWithIndexAndSector(List<String> stockCodeList) {

        BooleanBuilder whereClause = new BooleanBuilder();

        if (stockCodeList != null && !stockCodeList.isEmpty()) {
            whereClause.and(yhStockCode.stockCd.in(stockCodeList));
        }

        return queryFactory
                .select(new QStockCompositeDto(
                        yhStockCode.stockCd,
                        yhStockCode.stockNameKr,
                        yhStockCode.excngId,
                        infostockTheme.themeNm
                ))
                .from(yhStockCode)
                .where(whereClause)
                .leftJoin(infostockThemeStock)
                .on(yhStockCode.stockCd.eq(infostockThemeStock.stockCd))
                .leftJoin(infostockTheme)
                .on(infostockTheme.themeCd.eq(infostockThemeStock.infostockTheme.themeCd))
                .fetch();
    }

    @Override
    public List<RateOfReturnDto> findStockRateOfReturn(List<Watchlist> v, String targetDate) {

        v.forEach(item -> {

            queryFactory
                    .select(new QRateOfReturnDto(
                            yhStockCode.stockCd,
                            Expressions.cases()
                                    .when(yhMarket.stdDt.eq(item.getStandardDate()))
                                    .then(yhMarket.close)
                                    .otherwise(String.valueOf(0)).as("standardDateClose").length(),
                            Expressions.cases()
                                    .when(yhMarket.stdDt.ne(item.getStandardDate()))
                                    .then(yhMarket.close)
                                    .otherwise(String.valueOf(0)).as("targetDateClose").length(),
                            Expressions.cases()
                                    .when(yhMarket.stdDt.eq(item.getStandardDate()))
                                    .then(
                                            Expressions.stringTemplate("({0} - {1}) / {1} * 100",
                                                    yhMarket.close,   // targetDtClose
                                                    Expressions.cases() // stdDtClose 값 설정
                                                            .when(yhMarket.stdDt.eq(item.getStandardDate()))
                                                            .then(yhMarket.close)
                                                            .otherwise(String.valueOf(0)))
                                    )
                                    .otherwise(String.valueOf(0)).as("rateOfReturn").length()
                    ))
                    .from(yhStockCode)
                    .where(yhStockCode.stockCd.eq(item.getItemId()))
                    .leftJoin(yhMarket.yhStockCode, yhStockCode)
                    .where(yhMarket.stdDt.in(item.getStandardDate(), targetDate))
                    .fetchOne();

        });

//        List<RateOfReturnDto> results = queryFactory
//                .select(new QRateOfReturnDto(
//                        yhMarket.stockCd,
//                        // 기준일 가격
//                        yhMarket.price
//                                .when(yhMarket.stdDt.eq(item.getStandardDate()))
//                                .then(yhMarket.price)
//                                .otherwise(null).as("startPrice"),
//                        // 타겟일 가격
//                        yhMarket.price
//                                .when(yhMarket.stdDt.eq(targetDate))
//                                .then(yhMarket.price)
//                                .otherwise(null).as("endPrice")
//                ))
//                .from(yhMarket)
//                .where(yhMarket.stockCd.in(v.stream().map(Item::getItemId).collect(Collectors.toList())))
//                .groupBy(yhMarket.stockCd) // 종목 코드별로 그룹화
//                .fetch();
//
//        // 결과 변환
//        List<RateOfReturnDto> rateOfReturns = results.stream().map(tuple -> {
//            String stockCode = tuple.get(yhMarket.stockCd);
//            BigDecimal startPrice = tuple.get("startPrice", BigDecimal.class);
//            BigDecimal endPrice = tuple.get("endPrice", BigDecimal.class);
//            return new RateOfReturnDto(stockCode, startPrice, endPrice);
//        }).collect(Collectors.toList());

        return null;
    }

}
