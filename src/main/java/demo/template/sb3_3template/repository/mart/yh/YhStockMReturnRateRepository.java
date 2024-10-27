package demo.template.sb3_3template.repository.mart.yh;

import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;
import demo.template.sb3_3template.repository.custom.yh.CustomYhStockMReturnRateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YhStockMReturnRateRepository extends JpaRepository<YhStockMReturnRate, YhStockMReturnRate.CompositeKey>, CustomYhStockMReturnRateRepository {

}
