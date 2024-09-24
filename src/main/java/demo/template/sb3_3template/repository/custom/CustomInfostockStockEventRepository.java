package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.StockWithEventDto;

import java.util.List;

public interface CustomInfostockStockEventRepository {

    List<StockWithEventDto> findStockWithEvent(List<String> stockNameList);

}
