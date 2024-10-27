package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.entity.mart.yh.YhEcoMReturnRate;

import java.util.List;

public interface CustomYhEcoMReturnRateRepository {

    List<YhEcoMReturnRate> findEcoNmAndEcoDtGoe(String ecoNm, String ecoDt);

}
