package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.entity.mart.YhStockReturnRate;

import java.util.List;
import java.util.Optional;

public interface CustomYhEcoReturnRateRepository {

    Optional<YhStockReturnRate> findTest(String ss001, String isin001, String number, int i);

}
