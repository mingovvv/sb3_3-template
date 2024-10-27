package demo.template.sb3_3template.repository.mart.infostock;

import demo.template.sb3_3template.entity.mart.infostock.InfostockThemeStockMaster;
import demo.template.sb3_3template.repository.custom.infostock.CustomInfostockThemeStockMasterRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfostockThemeStockMasterRepository extends JpaRepository<InfostockThemeStockMaster, InfostockThemeStockMaster.CompositeKey>, CustomInfostockThemeStockMasterRepository {
}
