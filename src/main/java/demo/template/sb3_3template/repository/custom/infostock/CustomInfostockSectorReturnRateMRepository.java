package demo.template.sb3_3template.repository.custom.infostock;

import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRateM;

import java.util.List;

public interface CustomInfostockSectorReturnRateMRepository{

    List<InfostockSectorReturnRateM> findByThemeNmAndThemeDtGoe(String sectorNm, String themeDt);

}
