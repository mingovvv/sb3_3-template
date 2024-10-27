package demo.template.sb3_3template.repository.mart.infostock;

import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRate;
import demo.template.sb3_3template.repository.custom.CustomInfostockSectorReturnRateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfostockSectorReturnRateRepository extends JpaRepository<InfostockSectorReturnRate, InfostockSectorReturnRate.CompositeKey>, CustomInfostockSectorReturnRateRepository {
}
