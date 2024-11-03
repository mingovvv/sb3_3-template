package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.dto.projection.IndexReturnCountry;
import demo.template.sb3_3template.entity.mart.YhEcoClose;
import demo.template.sb3_3template.enums.RankType;

import java.util.List;
import java.util.Map;

public interface CustomYhEcoCloseRepository {

    List<YhEcoClose> findEcoNmAndStdDtGoe(String ecoNm, String number, String dayType);

    Map<RankType, List<IndexReturnCountry>> findEcoBy(String start, String end);

}
