package demo.template.sb3_3template.repository.custom;

import demo.template.common.annotation.TrackQueries;
import demo.template.sb3_3template.dto.*;
import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.entity.News;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.YhStockCode;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.enums.BsnsDays;
import demo.template.sb3_3template.enums.RankType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomYhStockCodeRepository {

    @TrackQueries
    List<YhStockCode> findStockWithIndexAndSector(List<String> stockCodeList);

    List<RateOfReturnDto> findStockRateOfReturn(List<Watchlist> v, String targetDate);

    Optional<StockRateOfReturnDto> findStockRateOfReturn(String stockName, int bsnsDays, String stdDt);

    List<StockWithMaxThemeDto> getStocksWithMaxCapTheme();

    List<NewsDto> getTopNews();

    Map<RankType, List<StockReturnMkCap>> findByStdDtAndBsnsDays(String stdDt, BsnsDays bsnsDays);
}
