package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.YhEcoCode;
import demo.template.sb3_3template.entity.mart.YhStockCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface YhEcoCodeRepository extends JpaRepository<YhEcoCode, String> {

    List<YhEcoCode> findByType(String type);

    Optional<YhEcoCode> findByTypeAndEcoCode(String type, String ecoCode);

}
