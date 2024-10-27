package demo.template.sb3_3template.repository.mart.infostock;

import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.repository.custom.CustomInfostockSectorIndexRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfostockSectorIndexRepository extends JpaRepository<InfostockSectorIndex, InfostockSectorIndex.CompositeKey>, CustomInfostockSectorIndexRepository {

    List<InfostockSectorIndex> findTop10ByStdDtOrderByIdxCalMkCapAsc(String stdDt);

}
