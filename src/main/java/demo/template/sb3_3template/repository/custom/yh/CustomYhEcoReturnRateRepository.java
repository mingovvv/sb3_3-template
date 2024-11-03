package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.dto.projection.IndexReturnCountry;
import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.enums.BsnsDays;
import demo.template.sb3_3template.enums.RankType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomYhEcoReturnRateRepository {

    Optional<YhStockReturnRate> findTest(String ss001, String isin001, String number, int i);

    Map<RankType, List<IndexReturnCountry>> findLastestIndicatorsByEcoTypeAndBsnsDays(String s, BsnsDays bsnsDays);

}
