package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.StockCompositeDto;

import java.util.List;

public interface CustomInfostockThemeRepository {

    List<StockCompositeDto> findAllSectorWithStock();

}
