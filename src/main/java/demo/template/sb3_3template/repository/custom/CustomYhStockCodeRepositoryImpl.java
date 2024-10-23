package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.*;
import demo.template.sb3_3template.entity.News;
import demo.template.sb3_3template.entity.QNews;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.YhStockCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
import static demo.template.sb3_3template.entity.mart.QInfostockSectorIndex.infostockSectorIndex;
import static demo.template.sb3_3template.entity.mart.QThemeStockMaster.themeStockMaster;
import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;
import static demo.template.sb3_3template.entity.mart.QYhStockReturnRate.yhStockReturnRate;
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
                                // 타겟일자 종가
                                Expressions.cases()
                                        .when(yhMarket.stdDt.eq(targetDate))
                                        .then(yhMarket.close.castToNum(Integer.class))
                                        .otherwise(0),
                                // 기준일자 종가
                                Expressions.cases()
                                        .when(yhMarket.stdDt.eq(item.getStandardDate()))
                                        .then(yhMarket.close.castToNum(Integer.class))
                                        .otherwise(0)
                                        .as("rateOfReturn")
                )))
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

    @Override
    public Optional<StockRateOfReturnDto> findStockRateOfReturn(String stockName, int bsnsDays, String stdDt) {

        YhStockCode yhstock = queryFactory.
                selectFrom(yhStockCode)
                .where(yhStockCode.stockNameKr.eq(stockName))
                .fetchOne();

        StockRateOfReturnDto stockRateOfReturnDto = queryFactory
                .select(new QStockRateOfReturnDto(
                        yhStockReturnRate.yhStockCode.excngId,
                        yhStockReturnRate.yhStockCode.stockCd,
                        yhStockReturnRate.returnRate
                ))
                .from(yhStockReturnRate)
                .where(yhStockReturnRate.yhStockCode.eq(yhstock)
                        .and(yhStockReturnRate.bsnsDays.eq(bsnsDays).and(yhStockReturnRate.stdDt.eq(stdDt))))
                .fetchOne();

        return Optional.ofNullable(stockRateOfReturnDto);

    }

    @Override
    public List<StockWithMaxThemeDto> getStocksWithMaxCapTheme() {
        return queryFactory
                .select(new QStockWithMaxThemeDto(
                        yhStockCode.stockCd,
                        yhStockCode.stockNameKr,
                        infostockSectorIndex.themeNm.max()
                ))
                .from(yhStockCode)
                .leftJoin(themeStockMaster).on(yhStockCode.stockCd.eq(themeStockMaster.stockCd))
                .leftJoin(infostockSectorIndex).on(themeStockMaster.themeCd.eq(infostockSectorIndex.themeCd)
                        .and(infostockSectorIndex.stdDt.eq("20241022")))
                .groupBy(yhStockCode.stockCd, yhStockCode.stockNameKr)
                .orderBy(infostockSectorIndex.idxCalMkCap.desc())
//                .orderBy(Expressions.numberPath(Long.class, "idx_cal_mk_cap").desc())
                .fetch();
    }

    @Override
    public List<NewsDto> getTopNews() {
        QNews news = QNews.news;

        // 서브쿼리: 각 월별로 stmt_score가 가장 큰 값을 찾는 부분
        QNews newsSub = new QNews("newsSub");

        List<NewsDto> result = queryFactory
                .select(Projections.constructor(NewsDto.class,
                        news.newsId,
                        news.vendor,
                        news.newsDt,
                        news.title,
                        news.text,
                        news.url,
                        news.docId,
                        news.sectorNm,
                        news.stmtScore))
                .from(news)
                .where(
                        // news_dt의 앞 6자리를 추출하여 월별로 stmt_score가 가장 큰 행을 찾는 조건
                        JPAExpressions.select(newsSub.stmtScore.max())
                                .from(newsSub)
                                .where(stringTemplate("substring({0}, 1, 6)", news.newsDt)
                                        .eq(stringTemplate("substring({0}, 1, 6)", newsSub.newsDt)))
                                .groupBy(stringTemplate("substring({0}, 1, 6)", newsSub.newsDt))
                                .having(news.stmtScore.eq(newsSub.stmtScore.max()))
                                .exists()
                )
                .orderBy(news.newsDt.asc())
                .fetch();

        return result;
    }


}
