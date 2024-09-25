package demo.template.sb3_3template.repository.mart;

import demo.template.sb3_3template.entity.mart.YhStockCode;
import demo.template.sb3_3template.repository.custom.CustomYhStockCodeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface YhStockCodeRepository extends JpaRepository<YhStockCode, YhStockCode.CompositeKey>, CustomYhStockCodeRepository {

    Optional<YhStockCodeRepository> findByStockCd(String stockCode);

    @Query("SELECT y FROM YhStockCode y LEFT JOIN FETCH y.yhStockReturnRates yr WHERE yr.bsnsDays = :bsnsDays AND yr.stdDt = :stdDt")
    Optional<YhStockCodeRepository> findStockReturnRate(@Param(value = "stockName") String stockName, @Param(value = "bsnsDays") String bsnsDays, @Param(value = "stdDt") String stdDt);

}
