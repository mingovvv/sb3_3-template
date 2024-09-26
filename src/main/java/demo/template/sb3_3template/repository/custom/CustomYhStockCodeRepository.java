package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.dto.StockRateOfReturnDto;
import demo.template.sb3_3template.dto.StockCompositeDto;
import demo.template.sb3_3template.entity.Watchlist;

import java.util.List;
import java.util.Optional;

public interface CustomYhStockCodeRepository {

    List<StockCompositeDto> findStockWithIndexAndSector(List<String> stockCodeList);

    List<RateOfReturnDto> findStockRateOfReturn(List<Watchlist> v, String targetDate);

    Optional<StockRateOfReturnDto> findStockRateOfReturn(String stockName, int bsnsDays, String stdDt);

}
