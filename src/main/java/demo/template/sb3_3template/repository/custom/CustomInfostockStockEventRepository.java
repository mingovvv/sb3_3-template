package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.EventOfStockDto;

import java.util.List;

public interface CustomInfostockStockEventRepository {

    List<EventOfStockDto> findEventOfStock(List<String> stockCodeList);

}
