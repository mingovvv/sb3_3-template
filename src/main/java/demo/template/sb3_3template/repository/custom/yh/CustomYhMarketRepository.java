package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.entity.mart.YhMarket;
import demo.template.sb3_3template.enums.RankType;

import java.util.List;
import java.util.Map;

public interface CustomYhMarketRepository {

    void findTest();

    List<YhMarket> findByStockNmAndStdDtGoe(String stockNm, String stdDt);

    Map<RankType, List<StockReturnMkCap>> findStockBy(String start, String end);

}
