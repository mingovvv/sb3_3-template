package demo.template.sb3_3template.service.widget;

import demo.template.common.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WG7Test {

    @Test
    void w19() {
        List<InfostockSectorReturnRate> returnList = List.of(
                new InfostockSectorReturnRate("TE001", "Technology", "20241021", 1, 4.2),
                new InfostockSectorReturnRate("TE001", "Technology", "20241021", 5, 4.3),
                new InfostockSectorReturnRate("TE001", "Technology", "20241021", 20, 4.4),
                new InfostockSectorReturnRate("TE002", "Finance", "20241021", 1, 4.2),
                new InfostockSectorReturnRate("TE002", "Finance", "20241021", 5, 4.2),
                new InfostockSectorReturnRate("TE002", "Finance", "20241021", 20, 4.2),
                new InfostockSectorReturnRate("TE003", "Healthcare", "20241021", 1, 4.2),
                new InfostockSectorReturnRate("TE003", "Healthcare", "20241021", 5, 4.2),
                new InfostockSectorReturnRate("TE003", "Healthcare", "20241021", 20, 4.2)
        );

        Table table = transformToTable(returnList);
        System.out.println(JsonUtil.toJson(table));

        List<InfostockSectorIndex> closeList = List.of(
                new InfostockSectorIndex("TE001", "Technology", "20241001", "5000"),
                new InfostockSectorIndex("TE001", "Technology", "20241002", "6000"),
                new InfostockSectorIndex("TE001", "Technology", "20241003", "2000"),
                new InfostockSectorIndex("TE002", "Finance", "20241001", "6000"),
                new InfostockSectorIndex("TE002", "Finance", "20241002", "9000"),
                new InfostockSectorIndex("TE002", "Finance", "20241003", "2500"),
                new InfostockSectorIndex("TE003", "Healthcare", "20241001", "5400"),
                new InfostockSectorIndex("TE003", "Healthcare", "20241002", "5500"),
                new InfostockSectorIndex("TE003", "Healthcare", "20241003", "5000")
        );
        List<Dto> dtoList = Dto.transformToDTO(closeList);
        List<LineChart> charts = transformToLineCharts(dtoList);
        System.out.println(JsonUtil.toJson(charts));

        List<StocksInSectorDto> stocksInSectorDtos = List.of(
                new StocksInSectorDto("TE001", "Technology", "SS001", "삼성전자", "테마요약1", "기업요약1"),
                new StocksInSectorDto("TE001", "Technology", "SS002", "엘지전자", "테마요약1", "기업요약2"),
                new StocksInSectorDto("TE001", "Technology", "SS003", "대우전자", "테마요약1", "기업요약3"),
                new StocksInSectorDto("TE001", "Technology", "SS004", "가우스전자", "테마요약1", "기업요약4"),
                new StocksInSectorDto("TE002", "Finance", "EH001", "하나은행", "테마요약2", "기업요약5"),
                new StocksInSectorDto("TE002", "Finance", "EH002", "경남은행", "테마요약2", "기업요약6")
        );

        List<Table> tables = transformToTables(stocksInSectorDtos);
        System.out.println(JsonUtil.toJson(tables));


    }

    static Table transformToTable(List<InfostockSectorReturnRate> returnList) {
        List<String> headers = List.of("섹터명", "+1 수익률", "+5 수익률", "+20 수익률");

        Map<String, Map<Integer, Double>> groupedRates = returnList.stream()
                .collect(Collectors.groupingBy(
                        rate -> rate.themeNm,
                        Collectors.toMap(rate -> rate.bsnsDays, rate -> rate.rate)
                ));

        List<String[]> rows = groupedRates.entrySet().stream()
                .map(entry -> {
                    String themeNm = entry.getKey();
                    Map<Integer, Double> rateMap = entry.getValue();
                    return new String[] {
                            themeNm,
                            String.valueOf(rateMap.getOrDefault(1, 0.0)),
                            String.valueOf(rateMap.getOrDefault(5, 0.0)),
                            String.valueOf(rateMap.getOrDefault(20, 0.0))
                    };
                })
                .collect(Collectors.toList());

        return new Table("TABLE1", null, null, headers, rows);
    }

    static List<Table> transformToTables(List<StocksInSectorDto> stocksInSectorDtos) {
        Map<String, Table> tableMap = new HashMap<>();

        for (StocksInSectorDto dto : stocksInSectorDtos) {
            tableMap.computeIfAbsent(dto.themeCd, k -> new Table("TABLE2", dto.themeNm, dto.themeSummary, List.of("종목명", "기업요약"), new ArrayList<>()))
                    .getRows().add(new String[]{dto.stockNm, dto.stockSummary});
        }

        return new ArrayList<>(tableMap.values());
    }

    static List<LineChart> transformToLineCharts(List<Dto> dtoList) {
        Map<String, LineChart> chartMap = new HashMap<>();

        for (Dto dto : dtoList) {
            chartMap.computeIfAbsent(dto.themeNm, k -> new LineChart(k, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()))
                    .addData(dto.stdDt, dto.close, String.valueOf(dto.rate4days));
        }

        return new ArrayList<>(chartMap.values());
    }


}

@Getter
@AllArgsConstructor
class StocksInSectorDto {
    String themeCd; // 테마코드
    String themeNm; // 테마명
    String stockCd; // 종목코드
    String stockNm; // 종목명
    String themeSummary; // 테마요약
    String stockSummary; // 기업요약
}

@Getter
@AllArgsConstructor
class Table {
    private String classification; // "TABLE2" 고정값
    private String title; // 섹터명
    private String text; // 테마요약
    private List<String> headers; // ["종목명", "기업요약"]
    private List<String[]> rows;
}

@Getter
@AllArgsConstructor
class InfostockSectorReturnRate {
    String themeCd;
    String themeNm;
    String stdDt;
    Integer bsnsDays;
    double rate;
}

@Getter
@AllArgsConstructor
class InfostockSectorIndex {
    String themeCd;
    String themeNm;
    String stdDt;
    String close;
}

@Getter
@AllArgsConstructor
class LineChart {
    private String title;
    private List<String> x;
    private List<String> y;
    private List<String> value;
    public void addData(String date, String price, String rate) {
        this.x.add(date);
        this.y.add(price);
        this.value.add(rate);
    }
}

@Getter
@AllArgsConstructor
class Dto {
    String themeCd;
    String themeNm;
    String stdDt;
    String close;
    double rate4days;
    static List<Dto> transformToDTO(List<InfostockSectorIndex> closeList) {
        List<Dto> dtoList = new ArrayList<>();
        for (int i = 0; i < closeList.size(); i++) {
            InfostockSectorIndex current = closeList.get(i);
            double rate4days = 0.0;
            if (i + 4 < closeList.size() && current.themeCd.equals(closeList.get(i + 4).themeCd)) {
                double currentClose = Double.parseDouble(current.close);
                double futureClose = Double.parseDouble(closeList.get(i + 4).close);
                rate4days = Math.round(((futureClose - currentClose) / currentClose) * 100 * 100.0) / 100.0;
            }
            dtoList.add(new Dto(current.themeCd, current.themeNm, current.stdDt, current.close, rate4days));
        }
        return dtoList;
    }
}