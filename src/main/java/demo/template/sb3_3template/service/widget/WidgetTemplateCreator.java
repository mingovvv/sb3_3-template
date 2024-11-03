package demo.template.sb3_3template.service.widget;

import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.dto.projection.IndexReturnCountry;
import demo.template.sb3_3template.dto.projection.SectorReturn;
import demo.template.sb3_3template.dto.projection.StockInSectorDto;
import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.dto.widget.ItemRateValDto;
import demo.template.sb3_3template.dto.widget.MonthlyTrend;
import demo.template.sb3_3template.dto.widget.W11Dto;
import demo.template.sb3_3template.dto.widget.W18Dto;
import demo.template.sb3_3template.entity.fs.Fs;
import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRate;
import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;
import demo.template.sb3_3template.enums.*;
import demo.template.sb3_3template.model.WidgetResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        double[] matchRatios = TrendDirectionClassifier.calculateMatchRatios(sourceMonthlyTrend, targetMonthlyTrend);

        List<ItemRateValDto> top3 = sourceData.table().stream().sorted(Comparator.comparingDouble(ItemRateValDto::mRate).reversed()).limit(3).toList();
        List<ItemRateValDto> bottom3 = sourceData.table().stream().sorted(Comparator.comparingDouble(ItemRateValDto::mRate)).limit(3).toList();
        Map<String, Double> sourceMRetunMap = sourceData.table().stream().collect(Collectors.toMap(ItemRateValDto::yearMonth, ItemRateValDto::mRate));
        Map<String, Double> targetMRetunMap = targetData.table().stream().collect(Collectors.toMap(ItemRateValDto::yearMonth, ItemRateValDto::mRate));

        WidgetResponse.Widget.Supplement.Table sourceUpTable = WidgetResponse.Widget.Supplement.Table.builder()
                .title(String.format("%s 상승 시 %s 변화", sourceData.itemNm(), targetData.itemNm()))
                .headers(headers)
                .rows(top3.stream().map(s -> new String[]{String.valueOf(s.mRate()), String.valueOf(targetMRetunMap.getOrDefault(s.yearMonth(), 0.0))}).toList())
                .build();

        WidgetResponse.Widget.Supplement.Table sourceDownTable = WidgetResponse.Widget.Supplement.Table.builder()
                .title(String.format("%s 하락 시 %s 변화", sourceData.itemNm(), targetData.itemNm()))
                .headers(headers)
                .rows(bottom3.stream().map(s -> new String[]{String.valueOf(s.mRate()), String.valueOf(targetMRetunMap.getOrDefault(s.yearMonth(), 0.0))}).toList())
                .build();

        tables.add(sourceUpTable);
        tables.add(sourceDownTable);

        WidgetResponse.Widget.Supplement.LineChart sourceChart = WidgetResponse.Widget.Supplement.LineChart.builder()
                .xaxis(sourceData.graph().stream().map(W11Dto.Graph::stdDt).toList())
                .yaxis(sourceData.graph().stream().map(W11Dto.Graph::val).toList())
//                .additionalValue(sourceData.graph().stream().map(s -> {
//                    if (s.stdDt().endsWith("15")) {
//                        return String.valueOf(sourceMRetunMap.getOrDefault(s.stdDt().substring(0, 6), 0.0));
//                    } else {
//                        return "";
//                    }
//                }).toList())
                .additionalValue(sourceData.table().stream().map(s -> s.yearMonth() + "|" + s.mRate()).toList())
                .build();

        WidgetResponse.Widget.Supplement.LineChart targetChart = WidgetResponse.Widget.Supplement.LineChart.builder()
                .xaxis(targetData.graph().stream().map(W11Dto.Graph::stdDt).toList())
                .yaxis(targetData.graph().stream().map(W11Dto.Graph::val).toList())
//                .additionalValue(targetData.graph().stream().map(s -> {
//                    if (s.stdDt().endsWith("15")) {
//                        return String.valueOf(targetMRetunMap.getOrDefault(s.stdDt().substring(0, 6), 0.0));
//                    } else {
//                        return "";
//                    }
//                }).toList())
                .additionalValue(targetData.table().stream().map(s -> s.yearMonth() + "|" + s.mRate()).toList())
                .build();

        lineCharts.add(sourceChart);
        lineCharts.add(targetChart);

        List<WidgetResponse.Widget.Values> values = List.of(
                WidgetResponse.Widget.Values.builder().text(sourceData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(targetData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(matchRatios[0])).build(),
                WidgetResponse.Widget.Values.builder().text(sourceData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(sourceUpCnt)).build(),
                WidgetResponse.Widget.Values.builder().text(targetData.itemNm()).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(targetUpCnt)).build(),
                WidgetResponse.Widget.Values.builder().text(String.valueOf(matchRatios[1])).build(),
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

        List<String> headers = List.of("종목명", "동반 상승 확률(횟수)", "동반 하락 확률(횟수)", "상승 평균 수익률", "하락 평균 수익률");

        List<ItemRateValDto> sourceDto = sourceData.table();
        List<MonthlyTrend> source = TrendDirectionClassifier.analyzeTrendDirection(sourceData.itemCd(), sourceDto);

        Map<String, List<ItemRateValDto>> largeGroup = large.stream().collect(Collectors.groupingBy(ItemRateValDto::itemNm,
                Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().sorted(Comparator.comparing(ItemRateValDto::yearMonth)).collect(Collectors.toList()))));

        Map<String, List<ItemRateValDto>> middleGroup = middle.stream().collect(Collectors.groupingBy(ItemRateValDto::itemNm,
                Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().sorted(Comparator.comparing(ItemRateValDto::yearMonth)).collect(Collectors.toList()))));

        Map<String, List<ItemRateValDto>> smallGroup = small.stream().collect(Collectors.groupingBy(ItemRateValDto::itemNm,
                Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().sorted(Comparator.comparing(ItemRateValDto::yearMonth)).collect(Collectors.toList()))));

        Map<StockCategory, Map<String, List<ItemRateValDto>>> groupMap = Map.of(StockCategory.LARGE, largeGroup, StockCategory.MEDIUM, middleGroup, StockCategory.SMALL, smallGroup);

        List<WidgetResponse.Widget.Supplement.Table> tables = new ArrayList<>();

        groupMap.forEach((category, grouped) -> {

            List<String[]> xaxis = grouped.entrySet().stream().map(s -> {
                String key = s.getKey();
                List<ItemRateValDto> value = s.getValue();
                OptionalDouble average = value.stream().mapToDouble(ItemRateValDto::mRate).average();
                List<MonthlyTrend> monthlyTrends = TrendDirectionClassifier.analyzeTrendDirection(key, value);
                double[] matchRatios = TrendDirectionClassifier.calculateMatchRatios(source, monthlyTrends);
                return new String[]{key, String.format("%s|%s", matchRatios[0], (int) matchRatios[2]), String.format("%s|%s", matchRatios[1], (int) matchRatios[3]), String.valueOf(average)};
            }).toList();

            WidgetResponse.Widget.Supplement.Table table = WidgetResponse.Widget.Supplement.Table.builder()
                    .title(category.getDescription())
                    .headers(headers)
                    .rows(xaxis)
                    .build();

            tables.add(table);

        });

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

//        for (Map<String, List<ItemRateValDto>> groupMap : group) {
//
//            List<String[]> xaxis = groupMap.entrySet().stream().map(s -> {
//                String key = s.getKey();
//                List<ItemRateValDto> value = s.getValue();
//                OptionalDouble average = value.stream().mapToDouble(ItemRateValDto::mRate).average();
//                List<MonthlyTrend> monthlyTrends = TrendDirectionClassifier.analyzeTrendDirection(key, value);
//                double[] matchRatios = TrendDirectionClassifier.calculateMatchRatios(source, monthlyTrends);
//                return new String[]{key, String.format("%s|%s",matchRatios[0], (int) matchRatios[2]), String.format("%s|%s",matchRatios[1], (int) matchRatios[3]), String.valueOf(average)};
//            }).toList();
//
//            WidgetResponse.Widget.Supplement.Table table = WidgetResponse.Widget.Supplement.Table.builder()
//                    .title(StockCategory.LARGE.getDescription())
//                    .headers(headers)
//                    .rows(xaxis)
//                    .build();
//
//            tables.add(table);
//        }

//        for (Map<String, List<ItemRateValDto>> groupMap : group) {
//
//            List<String[]> xaxis = groupMap.entrySet().stream().map(s -> {
//                String key = s.getKey();
//                List<ItemRateValDto> value = s.getValue();
//                OptionalDouble average = value.stream().mapToDouble(ItemRateValDto::mRate).average();
//                List<MonthlyTrend> monthlyTrends = TrendDirectionClassifier.analyzeTrendDirection(key, value);
//                double[] matchRatios = TrendDirectionClassifier.calculateMatchRatios(source, monthlyTrends);
//                return new String[]{key, String.format("%s|%s",matchRatios[0], (int) matchRatios[2]), String.format("%s|%s",matchRatios[1], (int) matchRatios[3]), String.valueOf(average)};
//            }).toList();
//
//            WidgetResponse.Widget.Supplement.Table table = WidgetResponse.Widget.Supplement.Table.builder()
//                    .title(StockCategory.LARGE.getDescription())
//                    .headers(headers)
//                    .rows(xaxis)
//                    .build();
//
//            tables.add(table);
//        }


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
