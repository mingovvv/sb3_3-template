package demo.template.sb3_3template.repository.mart.yh;

import demo.template.sb3_3template.entity.mart.YhEcoClose;
import demo.template.sb3_3template.repository.custom.yh.CustomYhEcoCloseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YhEcoCloseRepository extends JpaRepository<YhEcoClose, YhEcoClose.CompositeKey>, CustomYhEcoCloseRepository {
}
