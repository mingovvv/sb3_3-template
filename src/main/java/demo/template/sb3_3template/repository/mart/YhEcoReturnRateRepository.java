package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface YhEcoReturnRateRepository extends JpaRepository<YhEcoReturnRate, YhEcoReturnRate.CompositeKey> {

    @Query("SELECT y FROM YhEcoReturnRate y WHERE y.yhEcoCode = :yhEcoCode AND y.bsnsDays = :bsnsDays AND y.stdDt = :stdDt")
    Optional<YhEcoReturnRate> findByEcoCodeAndBsnsDaysAndStdDt(@Param("yhEcoCode") String yhEcoCode, @Param("bsnsDays") String bsnsDays, @Param("stdDt") String stdDt);

}