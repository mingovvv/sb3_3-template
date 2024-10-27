package demo.template.sb3_3template.repository.custom.infostock;

import demo.template.sb3_3template.dto.projection.StockInSectorDto;

import java.util.List;

public interface CustomInfostockThemeStockMasterRepository {

    List<StockInSectorDto> findBySectorCdAndStdDt(List<String> strings, String s);

}
