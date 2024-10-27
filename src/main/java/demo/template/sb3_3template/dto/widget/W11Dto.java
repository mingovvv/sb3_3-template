package demo.template.sb3_3template.dto.widget;

import demo.template.sb3_3template.entity.mart.YhEcoClose;
import demo.template.sb3_3template.entity.mart.YhEcoCode;
import demo.template.sb3_3template.entity.mart.YhMarket;
import demo.template.sb3_3template.entity.mart.YhStockCode;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRateM;
import demo.template.sb3_3template.entity.mart.yh.YhEcoMReturnRate;
import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;
import demo.template.sb3_3template.enums.TrendDirection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record W11Dto(
        String itemCd,
        String itemNm,
        List<Graph> graph,
        List<ItemRateValDto> table
) {

    public record Graph(
            String val,
            String stdDt
    ) {

    }

    public static W11Dto fromStock(List<YhStockMReturnRate> stockMReturn, List<YhMarket> stockClose) {
        YhStockCode stock = stockMReturn.get(0).getYhStockCode();
        List<Graph> graph = stockClose.stream().map(s -> new Graph(s.getClose(), s.getStdDt())).toList();
        List<ItemRateValDto> tables = stockMReturn.stream().map(s -> new ItemRateValDto(s.getStockCd(), s.getYhStockCode().getStockNameKr(), s.getStockDt(), s.getReturnRateM(), null)).toList();
        return new W11Dto(stock.getStockCd(), stock.getStockNameKr(), graph, tables);
    }

    public static W11Dto fromSector(List<InfostockSectorReturnRateM> sectorMReturn, List<InfostockSectorIndex> sectorClose) {
        InfostockSectorReturnRateM sector = sectorMReturn.get(0);
        List<Graph> graph = sectorClose.stream().map(s -> new Graph(s.getClose(), s.getStdDt())).toList();
        List<ItemRateValDto> tables = sectorMReturn.stream().map(s -> new ItemRateValDto(s.getThemeCd(), s.getThemeNm(), s.getThemeDt(), s.getReturnRate(), null)).toList();
        return new W11Dto(sector.getThemeCd(), sector.getThemeNm(), graph, tables);
    }

    public static W11Dto fromEco(List<YhEcoMReturnRate> ecoMReturn, List<YhEcoClose> ecoDayClose, List<YhEcoClose> ecoMonthClose) {

        Map<String, String> ecoValMap = ecoMonthClose.stream().collect(Collectors.toMap(YhEcoClose::getStdDt, YhEcoClose::getEcoVal));

        YhEcoCode eco = ecoMReturn.get(0).getYhEcoCode();
        List<Graph> graph = ecoDayClose.stream().map(s -> new Graph(s.getEcoVal(), s.getStdDt())).toList();
        List<ItemRateValDto> tables = ecoMReturn.stream().map(s -> new ItemRateValDto(s.getEcoCode(), s.getYhEcoCode().getEcoNameKr(), s.getEcoDt(), s.getReturnRateM(), ecoValMap.getOrDefault(s.getEcoDt(), "0"))).toList();
        return new W11Dto(eco.getEcoCode(), eco.getEcoNameKr(), graph, tables);
    }

}
