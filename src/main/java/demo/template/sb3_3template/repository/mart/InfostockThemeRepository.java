package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.InfostockTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfostockThemeRepository extends JpaRepository<InfostockTheme, InfostockTheme.CompositeKey> {

    List<InfostockTheme> findByStdDt(String stdDt);

}
