package demo.template.sb3_3template.service.widget;

import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.dto.projection.IndexReturnCountry;
import demo.template.sb3_3template.dto.projection.SectorReturn;
import demo.template.sb3_3template.dto.projection.StockInSectorDto;
import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.dto.widget.*;
import demo.template.sb3_3template.entity.fs.Fs;
import demo.template.sb3_3template.enums.*;
import demo.template.sb3_3template.model.WidgetResponse;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static demo.template.common.utils.DateUtil.YEAR_FORMATTER_KR;

public class WidgetTemplateCreator {

    public static WidgetResponse.Widget createWidget1Object(WidgetGroup.Widget widget5, String template, String... templateValues) {

        var val1 = templateValues[0] == null? "많은": "적은";
        var val2 = Integer.parseInt(templateValues[1]) > 6 ? "해외": "국내";

        return WidgetResponse.Widget.builder()
                .widgetNo(widget5.getWNo())
                .template(template)
                .values(List.of(
//                        WidgetResponse.Widget.Values.builder().text(val1).color("yellow").isBold(true).build(),
//                        WidgetResponse.Widget.Values.builder().text(val2).color("red").isBold(true).build()
                ))
                .build();
    }

    public static WidgetResponse.Widget createWidget2Object(WidgetGroup.Widget widget5, String template, String... templateValues) {

        var val1 = templateValues[0] == null? "많습니다.": "적습니다.";
        var val2 = Integer.parseInt(templateValues[1]) > 11100 ? "비쌉니다.": "저렴합니다.";

        return WidgetResponse.Widget.builder()
                .widgetNo(widget5.getWNo())
                .template(template)
                .values(List.of(
//                        WidgetResponse.Widget.Values.builder().text(val1).color("yellow").isBold(true).build(),
//                        WidgetResponse.Widget.Values.builder().text(val2).color("red").isBold(true).build()
                ))
                .build();
    }

    public static WidgetResponse.Widget createWidget3Object(WidgetGroup.Widget widget5, String template, String... templateValues) {
        var val1 = templateValues[0] == null? "기회입니다.": "기회가 아닙니다.";

        return WidgetResponse.Widget.builder()
                .widgetNo(widget5.getWNo())
                .template(template)
                .values(List.of(
//                        WidgetResponse.Widget.Values.builder().text(val1).color("yellow").isBold(true).build()
                ))
                .build();
    }

    public static WidgetResponse.Widget createWidget29Detail(Map<RankType, List<StockReturnMkCap>> stockReturnMap, Map<RankType, List<IndexReturnCountry>> ecoReturnRates, Map<RankType, List<SectorReturn>> sectorReturnMap, List<StockInSectorDto> stockInSector) {

        List<String> stockHeaders = List.of("종목명", "수익률", "시가총액");
        List<String> indexHeaders = List.of("지수명", "국가명", "수익률");
        List<String> sectorHeaders = List.of("섹터명", "수익률");
        List<String> sectorStockHeaders = List.of("종목명", "테마기업 요약", "시가총액");

        List<WidgetResponse.Widget.Supplement.Table> tables = new ArrayList<>();

        List<WidgetResponse.Widget.Supplement.Table> stockTables = stockReturnMap.keySet().stream().map(key -> {
            return WidgetResponse.Widget.Supplement.Table.builder()
                    .classfication("STOCK")
                    .title(key.name())
                    .headers(stockHeaders)
                    .rows(stockReturnMap.getOrDefault(key, Collections.emptyList()).stream()
                            .map(s -> new String[]{s.stockNm(), String.valueOf(s.returnRate()), s.idxCalMkCap()}).toList())
                    .build();
        }).toList();

        List<WidgetResponse.Widget.Supplement.Table> indexTables = ecoReturnRates.keySet().stream().map(key -> {
            return WidgetResponse.Widget.Supplement.Table.builder()
                    .classfication("INDEX")
                    .title(key.name())
                    .headers(indexHeaders)
                    .rows(ecoReturnRates.getOrDefault(key, Collections.emptyList()).stream()
                            .map(s -> new String[]{s.ecoNm(), s.country(), String.valueOf(s.returnRate())}).toList())
                    .build();
        }).toList();

        List<WidgetResponse.Widget.Supplement.Table> sectorTables = sectorReturnMap.keySet().stream().map(key -> {
            return WidgetResponse.Widget.Supplement.Table.builder()
                    .classfication("SECTOR")
                    .title(key.name())
                    .headers(sectorHeaders)
                    .rows(sectorReturnMap.getOrDefault(key, Collections.emptyList()).stream()
                            .map(s -> new String[]{s.sectorNm(), String.valueOf(s.returnRate())}).toList())
                    .build();
        }).toList();

        Map<String, WidgetResponse.Widget.Supplement.Table> tableMap = new HashMap<>();

        for (StockInSectorDto dto : stockInSector) {
            tableMap.computeIfAbsent(dto.themeCd(), k -> new WidgetResponse.Widget.Supplement.Table("SECTOR-STOCK", dto.themeNm(), dto.themeSummary(), sectorStockHeaders, new ArrayList<>()))
                    .getRows().add(new String[]{dto.stockNm(), dto.stockSummary(), dto.idxCalMkCap()});
        }

        ArrayList<WidgetResponse.Widget.Supplement.Table> sectorStockTables = new ArrayList<>(tableMap.values());

        tables.addAll(stockTables);
        tables.addAll(indexTables);
        tables.addAll(sectorTables);
        tables.addAll(sectorStockTables);

        return WidgetResponse.Widget.builder()
                .widgetNo(WidgetGroup.Widget.WIDGET_29.getWNo())
                .supplement(WidgetResponse.Widget.Supplement.builder()
                        .type(List.of("tables"))
                        .tables(tables)
                        .build())
                .build();
    }

    public static WidgetResponse.Widget createWidget11Detail(W11Dto sourceData, W11Dto targetData) {

        List<String> headers = List.of("기간", sourceData.itemNm(), targetData.itemNm());

        List<WidgetResponse.Widget.Supplement.Table> tables = new ArrayList<>();
        List<WidgetResponse.Widget.Supplement.LineChart> lineCharts = new ArrayList<>();

        List<MonthlyTrend> sourceMonthlyTrend = TrendDirectionClassifier.analyzeTrendDirection(sourceData.itemCd(), sourceData.table());
        List<MonthlyTrend> targetMonthlyTrend = TrendDirectionClassifier.analyzeTrendDirection(targetData.itemCd(), targetData.table());

        long sourceUpCnt = sourceMonthlyTrend.stream().filter(s -> s.direction() == TrendDirection.UP).count();
        long sourceDownCnt = sourceMonthlyTrend.stream().filter(s -> s.direction() == TrendDirection.DOWN).count();
        long targetUpCnt = targetMonthlyTrend.stream().filter(s -> s.direction() == TrendDirection.UP).count();
        long targetDownCnt = targetMonthlyTrend.stream().filter(s -> s.direction() == TrendDirection.DOWN).count();
        MatchRatios matchRatios = TrendDirectionClassifier.calculateMatchRatios(sourceMonthlyTrend, targetMonthlyTrend);

        List<ItemRateValDto> top3 = sourceData.table().stream().sorted(Comparator.comparingDouble(ItemRateValDto::mRate).reversed()).limit(3).toList();
        List<ItemRateValDto> bottom3 = sourceData.table().stream().sorted(Comparator.comparingDouble(ItemRateValDto::mRate)).limit(3).toList();

        Map<String, Double> targetMRetunMap = targetData.table().stream().collect(Collectors.toMap(ItemRateValDto::yearMonth, ItemRateValDto::mRate));

        WidgetResponse.Widget.Supplement.Table sourceUpTable = WidgetResponse.Widget.Supplement.Table.builder()
                .title(String.format("%s 상승 시 %s 변화", sourceData.itemNm(), targetData.itemNm()))
                .headers(headers)
                .rows(top3.stream().map(s -> new String[]{s.yearMonth(), String.valueOf(s.mRate()), String.valueOf(targetMRetunMap.getOrDefault(s.yearMonth(), 0.0))}).toList())
                .build();

        WidgetResponse.Widget.Supplement.Table sourceDownTable = WidgetResponse.Widget.Supplement.Table.builder()
                .title(String.format("%s 하락 시 %s 변화", sourceData.itemNm(), targetData.itemNm()))
                .headers(headers)
                .rows(bottom3.stream().map(s -> new String[]{s.yearMonth(), String.valueOf(s.mRate()), String.valueOf(targetMRetunMap.getOrDefault(s.yearMonth(), 0.0))}).toList())
                .build();

        tables.add(sourceUpTable);
        tables.add(sourceDownTable);

        WidgetResponse.Widget.Supplement.LineChart sourceChart = WidgetResponse.Widget.Supplement.LineChart.builder()
                .xaxis(sourceData.graph().stream().map(W11Dto.Graph::stdDt).toList())
                .yaxis(sourceData.graph().stream().map(W11Dto.Graph::val).toList())
                .build();

        WidgetResponse.Widget.Supplement.LineChart targetChart = WidgetResponse.Widget.Supplement.LineChart.builder()
                .xaxis(targetData.graph().stream().map(W11Dto.Graph::stdDt).toList())
                .yaxis(targetData.graph().stream().map(W11Dto.Graph::val).toList())
                .build();

        lineCharts.add(sourceChart);
        lineCharts.add(targetChart);

        List<WidgetResponse.Widget.Values> values = List.of(
                WidgetResponse.Widget.Values.builder().text(sourceData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(targetData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(matchRatios.upMatchRatio())).build(),
                WidgetResponse.Widget.Values.builder().text(sourceData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(sourceUpCnt)).build(),
                WidgetResponse.Widget.Values.builder().text(targetData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(targetUpCnt)).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(matchRatios.downMatchRatio())).build(),
                WidgetResponse.Widget.Values.builder().text(sourceData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(sourceDownCnt)).build(),
                WidgetResponse.Widget.Values.builder().text(targetData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(targetDownCnt)).build()
        );

        return WidgetResponse.Widget.builder()
                .widgetNo(WidgetGroup.Widget.WIDGET_11.getWNo())
                .values(values)
                .supplement(WidgetResponse.Widget.Supplement.builder()
                        .type(List.of("tables"))
                        .tables(tables)
                        .lineCharts(lineCharts)
                        .build())
                .build();
    }

    public static WidgetResponse.Widget createWidget18Detail(W18Dto sourceData, List<ItemRateValDto> large, List<ItemRateValDto> middle, List<ItemRateValDto> small) {

        String sourceName = sourceData.itemNm();
        List<MonthlyTrend> sourceTrends = TrendDirectionClassifier.analyzeTrendDirection(sourceData.itemCd(), sourceData.table());

        Map<String, List<ItemRateValDto>> largeGrouped = large.stream().filter(stock -> !stock.itemNm().equalsIgnoreCase(sourceName)).collect(Collectors.groupingBy(ItemRateValDto::itemNm));
        Map<String, List<ItemRateValDto>> middleGrouped = middle.stream().filter(stock -> !stock.itemNm().equalsIgnoreCase(sourceName)).collect(Collectors.groupingBy(ItemRateValDto::itemNm));
        Map<String, List<ItemRateValDto>> smallGrouped = small.stream().filter(stock -> !stock.itemNm().equalsIgnoreCase(sourceName)).collect(Collectors.groupingBy(ItemRateValDto::itemNm));

        Map<String, MatchRatios> largeRatios = largeGrouped.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, entry -> TrendDirectionClassifier.calculateMatchRatios(sourceTrends, TrendDirectionClassifier.analyzeTrendDirection(entry.getValue().get(0).itemCd(), entry.getValue()))
        ));

        Map<String, MatchRatios> middleRatios = middleGrouped.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, entry -> TrendDirectionClassifier.calculateMatchRatios(sourceTrends, TrendDirectionClassifier.analyzeTrendDirection(entry.getValue().get(0).itemCd(), entry.getValue()))
        ));

        Map<String, MatchRatios> smallRatios = smallGrouped.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, entry -> TrendDirectionClassifier.calculateMatchRatios(sourceTrends, TrendDirectionClassifier.analyzeTrendDirection(entry.getValue().get(0).itemCd(), entry.getValue()))
        ));

        List<String[]> largeUpTop3 = get3ItemsByRatio(largeRatios, TrendDirection.UP);
        List<String[]> middleUpTop3 = get3ItemsByRatio(middleRatios, TrendDirection.UP);
        List<String[]> smallUpTop3 = get3ItemsByRatio(smallRatios, TrendDirection.UP);

        List<String[]> largeDownTop3 = get3ItemsByRatio(largeRatios, TrendDirection.DOWN);
        List<String[]> middleDownTop3 = get3ItemsByRatio(middleRatios, TrendDirection.DOWN);
        List<String[]> smallDownTop3 = get3ItemsByRatio(smallRatios, TrendDirection.DOWN);

        List<WidgetResponse.Widget.Supplement.Table> tables = new ArrayList<>();

        tables.add(createTable("오를때|대형주", List.of("종목명", "동반상승확률(횟수)", "수익률"), largeUpTop3));
        tables.add(createTable("오를때|중형주", List.of("종목명", "동반상승확률(횟수)", "수익률"), middleUpTop3));
        tables.add(createTable("오를때|소형주", List.of("종목명", "동반상승확률(횟수)", "수익률"), smallUpTop3));
        tables.add(createTable("내릴때|대형주", List.of("종목명", "동반하락확률(횟수)", "수익률"), largeDownTop3));
        tables.add(createTable("내릴때|중형주", List.of("종목명", "동반하락확률(횟수)", "수익률"), middleDownTop3));
        tables.add(createTable("내릴때|소형주", List.of("종목명", "동반하락확률(횟수)", "수익률"), smallDownTop3));

        List<WidgetResponse.Widget.Values> values = List.of(
                WidgetResponse.Widget.Values.builder().text(sourceData.itemNm()).style("strong").build(),
                WidgetResponse.Widget.Values.builder().text("오를").style("strong").build(),
                WidgetResponse.Widget.Values.builder().text("상승").style("strong").build(),
                WidgetResponse.Widget.Values.builder().text(sourceData.itemNm()).style("strong").build(),
                WidgetResponse.Widget.Values.builder().text("내릴").style("strong").build(),
                WidgetResponse.Widget.Values.builder().text("하락").style("strong").build()
        );

        return WidgetResponse.Widget.builder()
                .widgetNo(WidgetGroup.Widget.WIDGET_18.getWNo())
                .values(values)
                .supplement(WidgetResponse.Widget.Supplement.builder()
                        .type(List.of("tables"))
                        .tables(tables)
                        .build())
                .build();

    }

    private static WidgetResponse.Widget.Supplement.Table createTable(String title, List<String> headers, List<String[]> rows) {
        return WidgetResponse.Widget.Supplement.Table.builder()
                .title(title)
                .headers(headers)
                .rows(rows)
                .build();
    }

    private static List<String[]> get3ItemsByRatio(Map<String, MatchRatios> ratios, TrendDirection direction) {
        return ratios.entrySet().stream()
                .sorted(Comparator.comparingDouble((Map.Entry<String, MatchRatios> entry) ->
                        direction == TrendDirection.UP ? entry.getValue().upMatchRatio() : entry.getValue().downMatchRatio()).reversed())
                .limit(3)
                .map(entry -> {

                    String itemName = entry.getKey();
                    MatchRatios matchRatios = entry.getValue();

                    return direction == TrendDirection.UP ?
                            new String[]{itemName, String.format("%s|%s", matchRatios.upMatchRatio(), matchRatios.upMatchCount()), String.valueOf(matchRatios.avgMonthlyReturnWhenUp())} :
                            new String[]{itemName, String.format("%s|%s", matchRatios.downMatchRatio(), matchRatios.downMatchCount()), String.valueOf(matchRatios.avgMonthlyReturnWhenDown())};

                })
                .collect(Collectors.toList());
    }

    public static WidgetResponse.Widget createWidget27Detail(WidgetCreationDto.Entity stockEntity, WidgetCreationDto.Entity financeEntity, String endYearOfPeriod, List<Fs> fsList) {

        List<String> headers = List.of("연도", financeEntity.entity(), "전년대비 성장률");

        // 순서 보장
        Map<String, Fs> fsMap = fsList.stream().collect(Collectors.toMap(Fs::getFsDt, Function.identity(), (v1, v2) -> v2, LinkedHashMap::new));

        List<String[]> returnRows = fsMap.keySet().stream().map(year -> {

            String[] rows = new String[0];

            Fs current = fsMap.get(year);
            BigDecimal value = current.getData();
            Double yoyPer = current.getYoyPer();

            if (ObjectUtils.isNotEmpty(yoyPer)) {
                // yoy 값이 존재하는 경우
                rows = new String[]{year, String.valueOf(value), String.valueOf(yoyPer)};
            } else {
                // yoy 값이 없는 경우
                String previousYear = String.valueOf(Integer.parseInt(year) - 1);

                if (fsMap.containsKey(previousYear)) {
                    Fs previous = fsMap.get(previousYear);
                    int previousSign = previous.getData().signum();
                    int currentSign = value.signum();

                    if (previousSign > 0 && currentSign < 0) {
                        rows = new String[]{year, String.valueOf(value), "적자전환"};
                    } else if (previousSign < 0 && currentSign > 0) {
                        rows = new String[]{year, String.valueOf(value), "흑자전환"};
                    } else {
                        rows = new String[]{year, String.valueOf(value), "적자지속"};
                    }

                }

                return rows;

            }

            return rows;
        }).limit(5).toList();

        WidgetResponse.Widget.Supplement.Table table = WidgetResponse.Widget.Supplement.Table.builder()
                .headers(headers)
                .rows(returnRows)
                .build();

        Fs fs = ObjectUtils.isNotEmpty(endYearOfPeriod) ? fsMap.get(endYearOfPeriod) : fsList.get(0);
        String year = DateUtil.formatYear(fs.getFsDt(), YEAR_FORMATTER_KR);
        BigDecimal val = fs.getData();

        // 위젯 값을 생성
        List<WidgetResponse.Widget.Values> values = List.of(
                WidgetResponse.Widget.Values.builder().text(year).build(),
                WidgetResponse.Widget.Values.builder().text(stockEntity.entity()).build(),
                WidgetResponse.Widget.Values.builder().text(financeEntity.entity()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(val)).build());

        return WidgetResponse.Widget.builder()
                .widgetNo(WidgetGroup.Widget.WIDGET_27.getWNo())
                .values(values)
                .supplement(WidgetResponse.Widget.Supplement.builder()
                        .type(List.of("table"))
                        .table(table)
                        .build())
                .build();
    }

//    private static String[] calculateYoYChange(Map<String, Fs> fsMap, String currentYear, BigDecimal currentValue) {
//        String previousYear = String.valueOf(Integer.parseInt(currentYear) - 1);
//        if (!fsMap.containsKey(previousYear)) {
//            return new String[0];
//        }
//
//        Fs previous = fsMap.get(previousYear);
//        int previousSign = previous.getData().signum();
//        int currentSign = currentValue.signum();
//
//        if (previousSign > 0 && currentSign < 0) {
//            return new String[]{currentYear, String.valueOf(currentValue), "적자전환"};
//        } else if (previousSign < 0 && currentSign > 0) {
//            return new String[]{currentYear, String.valueOf(currentValue), "흑자전환"};
//        } else {
//            return new String[]{currentYear, String.valueOf(currentValue), "적자지속"};
//        }
//    }

}
