package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.*;
import demo.template.sb3_3template.entity.News;
import demo.template.sb3_3template.entity.Watchlist;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomYhStockCodeRepository {

    List<StockCompositeDto> findStockWithIndexAndSector(List<String> stockCodeList);

    List<RateOfReturnDto> findStockRateOfReturn(List<Watchlist> v, String targetDate);

    Optional<StockRateOfReturnDto> findStockRateOfReturn(String stockName, int bsnsDays, String stdDt);

    List<StockWithMaxThemeDto> getStocksWithMaxCapTheme();

    List<NewsDto> getTopNews();

}
