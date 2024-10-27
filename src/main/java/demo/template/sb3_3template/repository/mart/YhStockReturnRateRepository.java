package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.repository.custom.CustomYhStockCodeRepository;
import demo.template.sb3_3template.repository.custom.yh.CustomYhEcoReturnRateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YhStockReturnRateRepository extends JpaRepository<YhStockReturnRate, YhStockReturnRate.CompositeKey>, CustomYhStockCodeRepository {



}
