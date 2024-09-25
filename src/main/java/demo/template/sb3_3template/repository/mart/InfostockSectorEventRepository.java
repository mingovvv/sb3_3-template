package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.InfostockSectorEvent;
import demo.template.sb3_3template.repository.custom.CustomInfostockSectorEventRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfostockSectorEventRepository extends JpaRepository<InfostockSectorEvent, InfostockSectorEvent.CompositeKey>, CustomInfostockSectorEventRepository {

}
