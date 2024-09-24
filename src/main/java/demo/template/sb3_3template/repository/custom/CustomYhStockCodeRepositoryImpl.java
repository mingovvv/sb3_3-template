package demo.template.sb3_3template.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QStockCompositeDto;
import demo.template.sb3_3template.dto.StockCompositeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QInfostockTheme.infostockTheme;
import static demo.template.sb3_3template.entity.mart.QInfostockThemeStock.infostockThemeStock;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;

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
                .on(infostockTheme.stdDt.eq(infostockThemeStock.infostockTheme.stdDt)
                        .and(infostockTheme.themeCd.eq(infostockThemeStock.infostockTheme.themeCd)))
                .fetch();
    }

}
