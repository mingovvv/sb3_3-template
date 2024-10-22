package demo.template.sb3_3template.service.widget;

import demo.template.common.utils.JsonUtil;
import demo.template.sb3_3template.dto.WidgetCreationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class WG1Test {

    @Test
    void w6() {

        // example
        WidgetCreationDto dto = new WidgetCreationDto(1, List.of(), null, List.of(
                new WidgetCreationDto.Widget6("이벤트1", "20241001"),
                new WidgetCreationDto.Widget6("이벤트2", "20241001"),
                new WidgetCreationDto.Widget6("이벤트3", "20241001"),
                new WidgetCreationDto.Widget6("이벤트4", "20241002"),
                new WidgetCreationDto.Widget6("이벤트5", "20241003")
        ), null);

        List<WidgetCreationDto.Widget6> widget6 = dto.widget6();

        Map<String, List<String>> eventDateToNamesMap = widget6.stream()
                .collect(Collectors.groupingBy(
                        WidgetCreationDto.Widget6::date,
                        Collectors.mapping(WidgetCreationDto.Widget6::event, Collectors.toList())
                ));

        // RDB 조회
        List<StockReturn> stockReturnList = List.of(new StockReturn("20241001", 4.2), new StockReturn("20241002", 5.6));

        List<Dto> list = stockReturnList.stream().flatMap(stockReturn -> {
            List<String> eventNames = eventDateToNamesMap.get(stockReturn.stdDate);
            return eventNames.stream().map(s -> new Dto(s, stockReturn.stdDate, stockReturn.close));
        }).toList();

        list.forEach(System.out::println);

    }

    static class StockReturn {
        String stdDate;
        double close;

        public StockReturn(String stdDate, double close) {
            this.stdDate = stdDate;
            this.close = close;
        }
    }

    static class Dto {
        String eventName;
        String eventDate;
        double close;

        public Dto(String eventName, String eventDate, double close) {
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.close = close;
        }

        @Override
        public String toString() {
            return "Dto{" +
                    "eventName='" + eventName + '\'' +
                    ", eventDate='" + eventDate + '\'' +
                    ", close=" + close +
                    '}';
        }
    }

    @Test
    void w7() {
        List<fnExpBsnsAvrg> fnExpBsnsAvrgs = List.of(
                new fnExpBsnsAvrg("T001", "삼성전자", "EXP001", "수익성", 20, "20241021", 35, 0.24),
                new fnExpBsnsAvrg("T001", "삼성전자", "EXP002", "가치", 20, "20241021", 20, 0.50),
                new fnExpBsnsAvrg("T001", "삼성전자", "EXP003", "규모", 20, "20241021", 11, 0.68),
                new fnExpBsnsAvrg("T001", "삼성전자", "EXP004", "가능성", 20, "20241021", 60, 0.12),
                new fnExpBsnsAvrg("T001", "삼성전자", "EXP005", "변동률", 20, "20241021", 20, 0.54)
        );
        List<fnFrBsnsAvrg> fnFrBsnsAvrgs = List.of(
                new fnFrBsnsAvrg("EXP001", "수익성", 20, "20241021", 15, 0.23),
                new fnFrBsnsAvrg("EXP001", "가치", 20, "20241021", 1, 0.04),
                new fnFrBsnsAvrg("EXP001", "규모", 20, "20241021", 12, 0.73),
                new fnFrBsnsAvrg("EXP001", "가능성", 20, "20241021", 5, 0.43),
                new fnFrBsnsAvrg("EXP001", "변동률", 20, "20241021", 17, 0.13)
        );

        RadarChart radarChart = fromRadarV1(fnExpBsnsAvrgs, fnFrBsnsAvrgs);
        System.out.println(JsonUtil.toJson(radarChart));

        List<fnItFrBsnsCntrbt> fnItFrBsnsCntrbts = List.of(
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP001", "수익성", 20, "20241021", 35),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP002", "가치", 20, "20241021", 20),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP003", "규모", 20, "20241021", 11),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP004", "가능성", 20, "20241021", 60),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP005", "변동률", 20, "20241021", 20),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP006", "시장규모", 20, "20241021", 21),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP007", "미래투자", 20, "20241021", 0),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP008", "고객충성도", 20, "20241021", 520),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP009", "우수성", 20, "20241021", 216),
            new fnItFrBsnsCntrbt("T001", "삼성전자", "EXP010", "자원효율", 20, "20241021", 224)
        );

        Table7 table7 = fromRadarV1TableV1(fnItFrBsnsCntrbts, fnFrBsnsAvrgs);
        System.out.println(JsonUtil.toJson(table7));

        String comparisonResult = compareScaling(fnExpBsnsAvrgs, fnFrBsnsAvrgs);
        System.out.println(comparisonResult);

        // 재정의
        List<FnExpBsnsDto> fnExpBsnsDtos = List.of(
                new FnExpBsnsDto("T001", "삼성전자", "EXP001", "수익성", 20, "20241021", 35, 0.24, 35),
                new FnExpBsnsDto("T001", "삼성전자", "EXP002", "가치", 20, "20241021", 20, 0.50, 20),
                new FnExpBsnsDto("T001", "삼성전자", "EXP003", "규모", 20, "20241021", 11, 0.68, 11),
                new FnExpBsnsDto("T001", "삼성전자", "EXP004", "가능성", 20, "20241021", 60, 0.12, 60),
                new FnExpBsnsDto("T001", "삼성전자", "EXP005", "변동률", 20, "20241021", 20, 0.54, 20),
                new FnExpBsnsDto("T001", "삼성전자", "EXP006", "시장규모", 20, "20241021", 21, 0.75, 21),
                new FnExpBsnsDto("T001", "삼성전자", "EXP007", "미래투자", 20, "20241021", 0, -0.25, 0),
                new FnExpBsnsDto("T001", "삼성전자", "EXP008", "고객충성도", 20, "20241021", 520, 1.0, 520),
                new FnExpBsnsDto("T001", "삼성전자", "EXP009", "우수성", 20, "20241021", 216, 0.85, 216),
                new FnExpBsnsDto("T001", "삼성전자", "EXP010", "자원효율", 20, "20241021", 224, 0.90, 224)
        );

        System.out.println("=====================");

        RadarChart radar = fromRadarV2(fnExpBsnsDtos, fnFrBsnsAvrgs);
        System.out.println(JsonUtil.toJson(radar));

        Table7 table71 = fromRadarV1TableV2(fnExpBsnsDtos);
        System.out.println(JsonUtil.toJson(table71));

        String s = compareScaling(fnExpBsnsDtos);
        System.out.println(s);

    }

    static RadarChart fromRadarV2(List<FnExpBsnsDto> fnExpBsnsDtos, List<fnFrBsnsAvrg> fnFrBsnsAvrgs) {
        // 순서가 중요한 고정 factors
        List<String> fixedFactors = List.of("가치", "수익성", "규모", "가능성", "변동률");

        // 각 Series를 채우기 위한 로직
        List<RadarChart.Series> seriesList = new ArrayList<>();

        // FnExpBsnsDto 데이터 -> 종목 데이터 Series 생성
        Map<String, Map<String, Double>> itemScalingMap = new HashMap<>();
        for (FnExpBsnsDto data : fnExpBsnsDtos) {
            itemScalingMap.computeIfAbsent(data.getItemNm(), k -> new HashMap<>()).put(data.getExpNm(), data.getScaling());
        }

        for (Map.Entry<String, Map<String, Double>> entry : itemScalingMap.entrySet()) {
            String itemName = entry.getKey();
            Map<String, Double> scalingMap = entry.getValue();

            // scaling 값을 고정 factors 순서에 맞게 정렬
            List<String> values = fixedFactors.stream()
                    .map(factor -> scalingMap.getOrDefault(factor, 0.0))
                    .map(String::valueOf)
                    .collect(Collectors.toList());

            seriesList.add(new RadarChart.Series(itemName, itemName, values));
        }

        // fnFrBsnsAvrg 데이터 -> "주식시장" Series 생성
        Map<String, Double> frScalingMap = fnFrBsnsAvrgs.stream()
                .collect(Collectors.toMap(fnFrBsnsAvrg::getExpNm, fnFrBsnsAvrg::getScaling));

        List<String> frValues = fixedFactors.stream()
                .map(factor -> frScalingMap.getOrDefault(factor, 0.0))
                .map(String::valueOf)
                .collect(Collectors.toList());

        seriesList.add(new RadarChart.Series("주식시장", "주식시장", frValues));

        return new RadarChart(fixedFactors, seriesList);
    }

    static Table7 fromRadarV1TableV2(List<FnExpBsnsDto> fnExpBsnsDtos) {
        // 헤더 정의
        String itemNm = fnExpBsnsDtos.isEmpty() ? "" : fnExpBsnsDtos.get(0).getItemNm();
        List<String> headers = List.of("익스포져명", "1개월 시장성과", itemNm + " 노출도");

        // 로우 데이터 생성
        List<String[]> rows = fnExpBsnsDtos.stream()
                .map(data -> new String[] {
                        data.getExpNm(),
                        String.valueOf(data.getExpAvrg()),
                        String.valueOf(data.getCtrbtVal())
                })
                .sorted((a, b) -> Double.compare(Double.parseDouble(b[2]), Double.parseDouble(a[2]))) // 노출도 순으로 내림차순 정렬
                .collect(Collectors.toList());

        return new Table7(null, null, null, headers, rows);
    }

    static String compareScaling(List<FnExpBsnsDto> fnExpBsnsDtos) {
        // 차이를 계산
        double diffSum = fnExpBsnsDtos.stream()
                .mapToDouble(data -> Math.abs(data.getScaling() - data.getExpAvrg()))
                .sum();

        // 일치 여부 판단
        return diffSum > 5 ? "일치" : "불일치";
    }

    static RadarChart fromRadarV1(List<fnExpBsnsAvrg> fnExpBsnsAvrgs, List<fnFrBsnsAvrg> fnFrBsnsAvrgs) {
        // 순서가 중요한 고정 factors
        List<String> fixedFactors = List.of("가치", "수익성", "규모", "가능성", "변동률");

        // 각 Series를 채우기 위한 로직
        List<RadarChart.Series> seriesList = new ArrayList<>();

        // fnExpBsnsAvrg 데이터 -> 종목 데이터 Series 생성
        Map<String, Map<String, Double>> itemScalingMap = new HashMap<>();
        for (fnExpBsnsAvrg data : fnExpBsnsAvrgs) {
            itemScalingMap.computeIfAbsent(data.getItemNm(), k -> new HashMap<>()).put(data.getExpNm(), data.getScaling());
        }

        for (Map.Entry<String, Map<String, Double>> entry : itemScalingMap.entrySet()) {
            String itemName = entry.getKey();
            Map<String, Double> scalingMap = entry.getValue();

            // scaling 값을 고정 factors 순서에 맞게 정렬
            List<String> values = fixedFactors.stream()
                    .map(factor -> scalingMap.getOrDefault(factor, 0.0))
                    .map(String::valueOf)
                    .collect(Collectors.toList());

            seriesList.add(new RadarChart.Series(itemName, itemName, values));
        }

        // fnFrBsnsAvrg 데이터 -> "주식시장" Series 생성
        Map<String, Double> frScalingMap = fnFrBsnsAvrgs.stream()
                .collect(Collectors.toMap(fnFrBsnsAvrg::getExpNm, fnFrBsnsAvrg::getScaling));

        List<String> frValues = fixedFactors.stream()
                .map(factor -> frScalingMap.getOrDefault(factor, 0.0))
                .map(String::valueOf)
                .collect(Collectors.toList());

        seriesList.add(new RadarChart.Series("주식시장", "주식시장", frValues));

        return new RadarChart(fixedFactors, seriesList);
    }

    static Table7 fromRadarV1TableV1(List<fnItFrBsnsCntrbt> fnItFrBsnsCntrbts, List<fnFrBsnsAvrg> fnFrBsnsAvrgs) {

        List<String> headers = List.of("익스포져명", "1개월 시장성과", fnItFrBsnsCntrbts.get(0).getItemNm() + " 노출도");

        // 로우 데이터 생성
        Map<String, Double> frAvrgMap = fnFrBsnsAvrgs.stream()
                .collect(Collectors.toMap(fnFrBsnsAvrg::getExpNm, fnFrBsnsAvrg::getFrAvrg));

        List<String[]> rows = fnItFrBsnsCntrbts.stream()
                .map(data -> new String[] {
                        data.getExpNm(),
                        String.valueOf(frAvrgMap.getOrDefault(data.getExpNm(), 0.0)),
                        String.valueOf(data.getCtrbtVal())
                })
                .sorted((a, b) -> Double.compare(Double.parseDouble(b[2]), Double.parseDouble(a[2])))
                .collect(Collectors.toList());

        return new Table7(null, null, null, headers, rows);
    }

    public static String compareScaling(List<fnExpBsnsAvrg> fnExpBsnsAvrgs, List<fnFrBsnsAvrg> fnFrBsnsAvrgs) {
        // Map으로 변환하여 빠르게 접근할 수 있도록 함
        Map<String, Double> expScalingMap = fnExpBsnsAvrgs.stream()
                .collect(Collectors.toMap(fnExpBsnsAvrg::getExpCd, fnExpBsnsAvrg::getScaling));

        // 차이를 계산
        double diffSum = fnFrBsnsAvrgs.stream()
                .filter(data -> expScalingMap.containsKey(data.getExpCd()))
                .mapToDouble(data -> Math.abs(expScalingMap.get(data.getExpCd()) - data.getScaling()))
                .sum();

        // 일치 여부 판단
        return diffSum < 5 ? "일치" : "불일치";
    }

    @Test
    void w8() throws ParseException {

        List<SectorSentimentScore> sentimentScores = List.of(
                new SectorSentimentScore("SEC001", "IT", "20241017", 0.85),
                new SectorSentimentScore("SEC001", "IT", "20241016", 0.45),
                new SectorSentimentScore("SEC001", "IT", "20241015", 0.23),
                new SectorSentimentScore("SEC001", "IT", "20241014", 0.12),
                new SectorSentimentScore("SEC001", "IT", "20241013", 0.34),
                new SectorSentimentScore("SEC002", "헬스케어", "20241017", 0.76),
                new SectorSentimentScore("SEC002", "헬스케어", "20241016", 0.22),
                new SectorSentimentScore("SEC002", "헬스케어", "20241015", 0.39),
                new SectorSentimentScore("SEC002", "헬스케어", "20241014", 0.15),
                new SectorSentimentScore("SEC002", "헬스케어", "20241013", 0.45),
                new SectorSentimentScore("SEC003", "에너지", "20241017", 0.67),
                new SectorSentimentScore("SEC003", "에너지", "20241016", 0.88),
                new SectorSentimentScore("SEC003", "에너지", "20241015", 0.19),
                new SectorSentimentScore("SEC003", "에너지", "20241014", 0.78),
                new SectorSentimentScore("SEC003", "에너지", "20241013", 0.33),
                new SectorSentimentScore("SEC004", "금융", "20241017", 0.54),
                new SectorSentimentScore("SEC004", "금융", "20241016", 0.21),
                new SectorSentimentScore("SEC004", "금융", "20241015", 0.67),
                new SectorSentimentScore("SEC004", "금융", "20241014", 0.11),
                new SectorSentimentScore("SEC004", "금융", "20241013", 0.25)
        );

        List<sectorSentimentIndex> sectorSentimentIndices = List.of(
                new sectorSentimentIndex("SEC001", "202410", 1.5),
                new sectorSentimentIndex("SEC002", "202410", -1.5),
                new sectorSentimentIndex("SEC003", "202410", 6.2),
                new sectorSentimentIndex("SEC004", "202410", -2.6));

        List<News> newsList = List.of(
                new News("hk", "20241014101010", "제목1", "내용1", "IT"),
                new News("hk", "20241015101010", "제목2", "내용2", "헬스케어"),
                new News("hk", "20241017101010", "제목3", "내용3", "에너지"),
                new News("hk", "20241014101010", "제목4", "내용4", "금융")
        );

        // LineChartW8 매핑 만들기
        Map<String, List<SectorSentimentScore>> sentimentMap = sentimentScores.stream()
                .collect(Collectors.groupingBy(SectorSentimentScore::getThemeNm));

        Map<String, List<News>> newsMap = newsList.stream()
                .collect(Collectors.groupingBy(News::getThemeNm));

        List<LineChartW8> lineCharts = new ArrayList<>();

        for (Map.Entry<String, List<SectorSentimentScore>> entry : sentimentMap.entrySet()) {
            String themeName = entry.getKey();
            List<SectorSentimentScore> scores = entry.getValue();

            List<String> x = scores.stream()
                    .map(SectorSentimentScore::getStdDt)
                    .collect(Collectors.toList());

            List<String> y = scores.stream()
                    .map(score -> String.valueOf(score.getStmtScore()))
                    .collect(Collectors.toList());

            List<String> value = new ArrayList<>();
            List<String> value2 = new ArrayList<>();
            List<News> relatedNews = newsMap.getOrDefault(themeName, Collections.emptyList());
            Map<String, String> newsMapByDate = new HashMap<>();

            for (News news : relatedNews) {
                String formattedDate = news.getNewsDt().substring(0, 8); // yyyyMMdd 형태로 자르기
                String newsValue = news.getNewsDt() + "|" + news.getTitle() + "|" + news.getText();
                newsMapByDate.put(formattedDate, newsValue);
                value.add(newsValue);
            }

            for (String date : x) {
                value2.add(newsMapByDate.getOrDefault(date, ""));
            }

            LineChartW8 lineChart = new LineChartW8(themeName, x, y, value, value2);
            lineCharts.add(lineChart);
        }

        System.out.println(JsonUtil.toJson(lineCharts));


    }
}

@Getter
@AllArgsConstructor
class fnExpBsnsAvrg {
    String itemCd; //  종목코드
    String itemNm; // 종목명
    String expCd; // 익스포져코드
    String expNm; // 익스포져명
    Integer bsnsDays; // 영업일 수
    String stdDt; // 기준일
    double expAvrg; // 익스포쳐 평균
    double scaling; //  min-max scaling
}

@Getter
@AllArgsConstructor
class fnFrBsnsAvrg {
    String expCd; // 익스포져코드
    String expNm; // 익스포져명
    Integer bsnsDays; // 영업일 수
    String stdDt; // 기준일
    double frAvrg; // 팩터리턴 평균
    double scaling; //  min-max scaling
}

@Getter
@AllArgsConstructor
class fnItFrBsnsCntrbt {
    String itemCd; //  종목코드
    String itemNm; // 종목명
    String expCd; // 익스포져코드
    String expNm; // 익스포져명
    Integer bsnsDays; // 영업일 수
    String stdDt; // 기준일
    double ctrbtVal; // 기여도
}

@Getter
@AllArgsConstructor
class RadarChart {
    private List<String> factors; // 익스포져명 고정값(순서 중요. 가치 -> 수익성 -> 규모 -> 가능성 -> 변동률 순)
    private List<Series> series;

    @Getter
    @AllArgsConstructor
    static class Series {
        private String category; // fnFrBsnsAvrg 객체의 경우 "주식시장" 고정값, fnExpBsnsAvrg 객체의 경우 종목명 참조
        private String displayName; // fnFrBsnsAvrg 객체의 경우 "주식시장" 고정값, fnExpBsnsAvrg 객체의 경우 종목명 참조
        private List<String> values; // 상위 객체의 RadarChart factors 순서와 일치하도록 double scaling; //  min-max scaling 값을 나열
    }
}

@Getter
@AllArgsConstructor
class Table7 {
    private String classification; // null
    private String title; // null
    private String text; // null
    private List<String> headers; // ["익스포져명", "1개월 시장성과", "{종목명} 노출도"]
    private List<String[]> rows; // 익스포져명, ctrbtVal, 익스포져 평균
}

@Getter
@AllArgsConstructor
class FnExpBsnsDto {
    String itemCd; //  종목코드
    String itemNm; // 종목명
    String expCd; // 익스포져코드
    String expNm; // 익스포져명
    Integer bsnsDays; // 영업일 수
    String stdDt; // 기준일
    double expAvrg; // 익스포쳐 평균
    double scaling; //  min-max scaling
    double ctrbtVal; // 기여도
}

@Getter
@AllArgsConstructor
class SectorSentimentScore {
    String themeCd; // 섹터코드
    String themeNm; // 섹터명
    String stdDt; // 기준일
    double stmtScore; // 감정점수
}

@Getter
@AllArgsConstructor
class sectorSentimentIndex {
    String themeCd; // 섹터코드
    String stmtDt; // yyyyMM
    double stmtChgRate; // 변화율
}

@Getter
@AllArgsConstructor
class News {
    String vendor; // 벤더사
    String newsDt; // 뉴스시간 yyyyMMddHHmmss
    String title; // 제목
    String text; // 본문
    String themeNm; // 섹터명
}
@Getter
@AllArgsConstructor
class LineChartW8 {
    private String title; // 섹터명
    private List<String> x; // 날짜
    private List<String> y; // stmtScore
    private List<String> value; // 뉴스정보 매핑해야함 : 뉴스시간 + '|' + 제목 + '|' + 본문
    private List<String> value2; // 뉴스정보 매핑해야함 : 뉴스시간 + '|' + 제목 + '|' + 본문 *** x축 날짜와 매칭되지 않으면 빈칸으로 넣어야 함
}