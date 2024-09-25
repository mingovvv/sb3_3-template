package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.YhStockCode;
import demo.template.sb3_3template.repository.custom.CustomYhStockCodeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YhStockCodeRepository extends JpaRepository<YhStockCode, YhStockCode.CompositeKey>, CustomYhStockCodeRepository {

    Optional<YhStockCodeRepository> findByStockCd(String stockCode);

}
