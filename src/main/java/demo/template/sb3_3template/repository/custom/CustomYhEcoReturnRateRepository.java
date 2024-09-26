package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.entity.Watchlist;

import java.util.List;

public interface CustomYhEcoReturnRateRepository {

    List<RateOfReturnDto> findIndesRateOfReturn(List<Watchlist> v, String targetDate);

}
