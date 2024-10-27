package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.entity.mart.YhMarket;

import java.util.List;

public interface CustomYhMarketRepository {

    void findTest();

    List<YhMarket> findByStockNmAndStdDtGoe(String stockNm, String stdDt);

}
