package demo.template.sb3_3template.dto.widget;

import demo.template.sb3_3template.entity.mart.YhEcoClose;
import demo.template.sb3_3template.entity.mart.YhEcoCode;
import demo.template.sb3_3template.entity.mart.YhMarket;
import demo.template.sb3_3template.entity.mart.YhStockCode;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRateM;
import demo.template.sb3_3template.entity.mart.yh.YhEcoMReturnRate;
import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record W18Dto(
        String itemCd,
        String itemNm,
        List<ItemRateValDto> table
) {

    public record Graph(
            String val,
            String stdDt
    ) {

    }

    public static W18Dto fromStock(List<YhStockMReturnRate> stockMReturn) {
        YhStockCode stock = stockMReturn.get(0).getYhStockCode();
        List<ItemRateValDto> tables = stockMReturn.stream().map(s -> new ItemRateValDto(s.getStockCd(), s.getYhStockCode().getStockNameKr(), s.getStockDt(), s.getReturnRateM(), null)).toList();
        return new W18Dto(stock.getStockCd(), stock.getStockNameKr(), tables);
    }

    public static W18Dto fromSector(List<InfostockSectorReturnRateM> sectorMReturn) {
        InfostockSectorReturnRateM sector = sectorMReturn.get(0);
        List<ItemRateValDto> tables = sectorMReturn.stream().map(s -> new ItemRateValDto(s.getThemeCd(), s.getThemeNm(), s.getThemeDt(), s.getReturnRate(), null)).toList();
        return new W18Dto(sector.getThemeCd(), sector.getThemeNm(), tables);
    }

    public static W18Dto fromEco(List<YhEcoMReturnRate> ecoMReturn, List<YhEcoClose> ecoMonthClose) {

        Map<String, String> ecoValMap = ecoMonthClose.stream().collect(Collectors.toMap(YhEcoClose::getStdDt, YhEcoClose::getEcoVal));

        YhEcoCode eco = ecoMReturn.get(0).getYhEcoCode();
        List<ItemRateValDto> tables = ecoMReturn.stream().map(s -> new ItemRateValDto(s.getEcoCode(), s.getYhEcoCode().getEcoNameKr(), s.getEcoDt(), s.getReturnRateM(), ecoValMap.getOrDefault(s.getEcoDt(), "0"))).toList();
        return new W18Dto(eco.getEcoCode(), eco.getEcoNameKr(), tables);
    }

}
