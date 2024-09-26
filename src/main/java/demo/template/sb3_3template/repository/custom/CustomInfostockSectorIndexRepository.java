package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.entity.Watchlist;

import java.util.List;

public interface CustomInfostockSectorIndexRepository {

    List<RateOfReturnDto> findSectorRateOfReturn(List<Watchlist> v, String minusDay);

}
