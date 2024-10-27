package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRate;
import demo.template.sb3_3template.enums.BsnsDays;
import demo.template.sb3_3template.enums.RankType;

import java.util.List;
import java.util.Map;

public interface CustomInfostockSectorReturnRateRepository {

    Map<RankType, List<InfostockSectorReturnRate>> findByStdDtAndBsnsDays(String stdDt, BsnsDays bsnsDays);

}
