package demo.template.sb3_3template.repository.mart.infostock;

import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRateM;
import demo.template.sb3_3template.repository.custom.infostock.CustomInfostockSectorReturnRateMRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfostockSectorReturnRateMRepository extends JpaRepository<InfostockSectorReturnRateM, InfostockSectorReturnRateM.CompositeKey>, CustomInfostockSectorReturnRateMRepository {
}
