package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.dto.StockCompositeDto;
import demo.template.sb3_3template.entity.Watchlist;

import java.util.List;

public interface CustomYhStockCodeRepository {

    List<StockCompositeDto> findStockWithIndexAndSector(List<String> stockCodeList);

    List<RateOfReturnDto> findStockRateOfReturn(List<Watchlist> v, String targetDate);

}
