package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.YhMarket;
import demo.template.sb3_3template.repository.custom.yh.CustomYhMarketRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YhMarketRepository extends JpaRepository<YhMarket, String>, CustomYhMarketRepository {
}
