package demo.template.sb3_3template.repository.custom.yh;

import demo.template.sb3_3template.entity.mart.YhEcoClose;

import java.util.List;

public interface CustomYhEcoCloseRepository {

    List<YhEcoClose> findEcoNmAndStdDtGoe(String ecoNm, String number, String dayType);

}
