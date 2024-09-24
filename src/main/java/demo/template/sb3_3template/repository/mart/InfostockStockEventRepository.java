package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.InfostockStockEvent;
import demo.template.sb3_3template.repository.custom.CustomInfostockStockEventRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfostockStockEventRepository extends JpaRepository<InfostockStockEvent, InfostockStockEvent.CompositeKey>, CustomInfostockStockEventRepository {
}
