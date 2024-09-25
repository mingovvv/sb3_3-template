package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QRateOfReturnDto;
import demo.template.sb3_3template.dto.QStockCompositeDto;
import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.dto.StockCompositeDto;
import demo.template.sb3_3template.entity.Watchlist;
import org.springframework.stereotype.Repository;

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
    public List<RateOfReturnDto> findStockRateOfReturn(List<Watchlist> stockList, String targetDate) {

        return stockList.stream().map(item -> queryFactory
                .select(new QRateOfReturnDto(
                        yhStockCode.stockCd,
                        Expressions.cases()
                                .when(yhMarket.stdDt.eq(item.getStandardDate()))
                                .then(yhMarket.close.castToNum(Integer.class))
                                .otherwise(0).as("standardDateClose"),
                        Expressions.cases()
                                .when(yhMarket.stdDt.ne(item.getStandardDate()))
                                .then(yhMarket.close.castToNum(Integer.class))
                                .otherwise(0).as("targetDateClose"),
                        Expressions.numberTemplate(Integer.class, "({0} - {1}) / {1} * 100",
                                        yhMarket.close.castToNum(Integer.class), // targetDtClose
                                        Expressions.cases() // stdDtClose 값 설정
                                                .when(yhMarket.stdDt.eq(yhMarket.stdDt))
                                                .then(yhMarket.close.castToNum(Integer.class))
                                                .otherwise(0))
                                .as("rateOfReturn")
                ))
                .from(yhStockCode)
                .where(yhStockCode.stockCd.eq(item.getItemId()))
                .leftJoin(yhMarket.yhStockCode, yhStockCode)
                .where(yhMarket.stdDt.in(item.getStandardDate(), targetDate))
                .fetchOne()).toList();

//        // List에 있는 item들의 stockId와 기준일들을 한 번에 처리하도록 개선
//        List<RateOfReturnDto> result = queryFactory
//                .select(new QRateOfReturnDto(
//                        yhStockCode.stockCd,
//                        Expressions.cases()
//                                .when(yhMarket.stdDt.eq(yhMarket.stdDt)) // 기준일에 대한 close 가격
//                                .then(yhMarket.close.castToNum(Integer.class))
//                                .otherwise(0).as("standardDateClose"),
//                        Expressions.cases()
//                                .when(yhMarket.stdDt.ne(yhMarket.stdDt)) // targetDate에 대한 close 가격
//                                .then(yhMarket.close.castToNum(Integer.class))
//                                .otherwise(0).as("targetDateClose"),
//                        Expressions.numberTemplate(Integer.class, "({0} - {1}) / {1} * 100",
//                                        yhMarket.close.castToNum(Integer.class), // targetDtClose
//                                        Expressions.cases() // stdDtClose 값 설정
//                                                .when(yhMarket.stdDt.eq(yhMarket.stdDt))
//                                                .then(yhMarket.close.castToNum(Integer.class))
//                                                .otherwise(0))
//                                .as("rateOfReturn")
//                ))
//                .from(yhStockCode)
//                .leftJoin(yhMarket).on(yhMarket.yhStockCode.eq(yhStockCode.stockCd))
//                .where(yhStockCode.stockCd.in(v.stream().map(Watchlist::getItemId).collect(Collectors.toList())),
//                        yhMarket.stdDt.in(v.stream().map(Watchlist::getStandardDate).collect(Collectors.toList()), targetDate))
//                .fetch();
//
//        return result;

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
    }

}
