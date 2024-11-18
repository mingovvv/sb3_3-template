package demo.template.common.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static demo.template.common.utils.DateUtil.getQuarterEndDates;

class DateUtilTest {

    @Test
    void test1() {
//        List<String> strings = List.of("20230102", "20230105", "20230301", "20230501");
        List<String> strings = Arrays.asList("20230329", "20230330", "20230629", "20230630", "20230928", "20230929", "20231228", "20231229");

        System.out.println(getQuarterEndDates(strings));

    }

    @Test
    void test2() {
        // xaxis 데이터 (yyyyMMdd 형식)
        List<String> xaxis = new ArrayList<>();
        xaxis.add("20220830");
        xaxis.add("20221220");
        xaxis.add("20230331");
        xaxis.add("20230630");
        xaxis.add("20230927");
        xaxis.add("20231228");
        xaxis.add("20240329");
        xaxis.add("20240628");
        xaxis.add("20240930");
        xaxis.add("20241113");

        // additionalValue 데이터 (yyyy-quarter 형식)
        List<String> additionalValue = new ArrayList<>();
//        additionalValue.add("2022-4|PER|8.54|영업이익");
//        additionalValue.add("2023-1|PER|9.54|영업이익");
//        additionalValue.add("2023-2|PER|10.54|영업이익");
//        additionalValue.add("2023-3|PER|11.54|영업이익");
//        additionalValue.add("2023-4|PER|12.54|영업이익");
        additionalValue.add("2024-1|PER|15.54|영업이익");
        additionalValue.add("2024-2|PER|14.54|영업이익");
        additionalValue.add("2024-3|PER|13.54|영업이익");
        additionalValue.add("2024-4|PER|13.54|영업이익");
        additionalValue.add("2025-1|PER|13.54|영업이익");

        // 메소드 호출로 필터링 수행
        // 메소드 호출로 필터링 수행
        List<String> filteredAdditionalValue = filterByQuarterFormatFromDates(additionalValue, xaxis);
        List<String> filteredXaxis = filterByDatesFromQuarterFormat(xaxis, additionalValue);

        // 결과 출력
        System.out.println("Filtered additionalValue:");
        filteredAdditionalValue.forEach(System.out::println);
        System.out.println();
        System.out.println("Filtered xaxis:");
        filteredXaxis.forEach(System.out::println);

    }

    public static List<String> filterByQuarterFormatFromDates(List<String> additionalValue, List<String> xaxis) {
        List<String> xaxisQuarters = convertXaxisToQuarters(xaxis);
        return additionalValue.stream()
                .filter(value -> {
                    String quarterInfo = value.split("\\|")[0]; // yyyy-quarter 추출
                    return xaxisQuarters.contains(quarterInfo); // xaxisQuarters에 포함된 분기인지 확인
                })
                .collect(Collectors.toList());
    }

    // additionalValue 리스트를 기반으로 xaxis 리스트 필터링
    public static List<String> filterByDatesFromQuarterFormat(List<String> xaxis, List<String> additionalValue) {
        List<String> additionalValueQuarters = convertAdditionalValueToQuarters(additionalValue);
        return xaxis.stream()
                .filter(date -> {
                    String year = date.substring(0, 4);
                    String month = date.substring(4, 6);
                    int quarter = (Integer.parseInt(month) - 1) / 3 + 1; // 월을 분기로 변환
                    String quarterInfo = year + "-" + quarter; // yyyy-quarter 형식으로 변환
                    return additionalValueQuarters.contains(quarterInfo); // additionalValueQuarters에 포함된 분기인지 확인
                })
                .collect(Collectors.toList());
    }

    // xaxis 리스트를 분기 형식(yyyy-quarter)으로 변환
    public static List<String> convertXaxisToQuarters(List<String> xaxis) {
        return xaxis.stream()
                .map(date -> {
                    String year = date.substring(0, 4);
                    String month = date.substring(4, 6);
                    int quarter = (Integer.parseInt(month) - 1) / 3 + 1; // 월을 분기로 변환
                    return year + "-" + quarter; // yyyy-quarter 형식으로 변환
                })
                .distinct()
                .collect(Collectors.toList());
    }

    // additionalValue 리스트를 분기 형식(yyyy-quarter)으로 변환
    public static List<String> convertAdditionalValueToQuarters(List<String> additionalValue) {
        return additionalValue.stream()
                .map(value -> value.split("\\|")[0]) // yyyy-quarter 추출
                .distinct()
                .collect(Collectors.toList());
    }

}


