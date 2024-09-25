package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YhStockReturnRateRepository extends JpaRepository<YhStockReturnRate, YhStockReturnRate.CompositeKey> {



}
