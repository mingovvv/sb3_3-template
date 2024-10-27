package demo.template.sb3_3template.repository.mart.yh;

import demo.template.sb3_3template.entity.mart.yh.YhEcoMReturnRate;
import demo.template.sb3_3template.repository.custom.yh.CustomYhEcoMReturnRateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YhEcoMReturnRateRepository extends JpaRepository<YhEcoMReturnRate, YhEcoMReturnRate.CompositeKey>, CustomYhEcoMReturnRateRepository {
}
