package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.dto.projection.StockMonthlyReturn;
import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;

import java.util.List;

public interface CustomYhStockMReturnRateRepository {

    List<YhStockMReturnRate> findByStockNmAndStockDtGoe(String stockNm, String stockDt);

    List<YhStockMReturnRate> findByStockDtGoe(String stockDt, String stdDt, String mkCapStart, String mkCapEnd);

    List<StockMonthlyReturn> findByStockDtGoe(String number, String number1);
}
