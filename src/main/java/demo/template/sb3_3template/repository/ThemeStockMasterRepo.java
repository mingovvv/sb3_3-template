package demo.template.sb3_3template.repository;

import demo.template.sb3_3template.entity.mart.ThemeStockMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeStockMasterRepo extends JpaRepository<ThemeStockMaster, ThemeStockMaster.CompositeKey> {
}
