package demo.template.sb3_3template.repository.custom.infostock;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.projection.QStockInSectorDto;
import demo.template.sb3_3template.dto.projection.StockInSectorDto;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QYhMarket.yhMarket;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;
import static demo.template.sb3_3template.entity.mart.infostock.QInfostockThemeMaster.infostockThemeMaster;
import static demo.template.sb3_3template.entity.mart.infostock.QInfostockThemeStockMaster.infostockThemeStockMaster;
import static demo.template.sb3_3template.entity.mart.raw.QInfostockStockSummary.infostockStockSummary;

public class CustomInfostockThemeStockMasterRepositoryImpl implements CustomInfostockThemeStockMasterRepository {

    private final JPAQueryFactory queryFactory;

    public CustomInfostockThemeStockMasterRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<StockInSectorDto> findBySectorCdAndStdDt(List<String> stockCdList, String stdDt) {
        return queryFactory
                .select(new QStockInSectorDto(
                        infostockThemeStockMaster.themeCd,
                        infostockThemeStockMaster.themeNm,
                        infostockThemeStockMaster.stockCd,
                        yhStockCode.stockNameKr,
                        infostockThemeMaster.ThemeSummary,
                        infostockStockSummary.stockSummary,
                        yhMarket.idxCalMkCap
                ))
                .from(infostockThemeStockMaster)
                .leftJoin(infostockThemeStockMaster.infostockThemeMaster, infostockThemeMaster)
                .leftJoin(infostockStockSummary)
                    .on(infostockThemeStockMaster.stockCd.eq(infostockStockSummary.stockCd))
                .leftJoin(yhMarket)
                    .on(infostockThemeStockMaster.stockCd.eq(yhMarket.yhStockCode.stockCd))
                .leftJoin(yhMarket.yhStockCode, yhStockCode)
                .where(infostockThemeStockMaster.themeCd.in(stockCdList)
                        .and(yhMarket.stdDt.eq(stdDt)))
                .fetch();
    }

}
