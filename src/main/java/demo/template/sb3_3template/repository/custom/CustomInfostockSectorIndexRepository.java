package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.dto.projection.SectorReturn;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.enums.RankType;

import java.util.List;
import java.util.Map;

public interface CustomInfostockSectorIndexRepository {

    List<RateOfReturnDto> findSectorRateOfReturn(List<Watchlist> v, String minusDay);

    List<InfostockSectorIndex> findByThemeNmAndStdDtGoe(String sectorNm, String stdDt);

    Map<RankType, List<SectorReturn>> findSectorBy(String start, String end);

}
