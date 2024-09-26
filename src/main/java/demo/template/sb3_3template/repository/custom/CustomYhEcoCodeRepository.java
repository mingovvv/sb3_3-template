package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.entity.Watchlist;

import java.util.List;

public interface CustomYhEcoCodeRepository {

    List<RateOfReturnDto> findIndexRateOfReturn(List<Watchlist> indexList, String minusDay, String dayType);

}
