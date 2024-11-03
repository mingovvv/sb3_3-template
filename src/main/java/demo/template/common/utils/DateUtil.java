package demo.template.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DateUtil {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("yyyy");
    public static final DateTimeFormatter YEAR_FORMATTER_KR = DateTimeFormatter.ofPattern("yyyy년");

    private DateUtil() {}

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String getMinusYear(int years) {
        return formatDateTime(now().minusYears(years), YEAR_FORMATTER);
    }

    public static String getMinusDay(int days) {
        return formatDateTime(now().minusDays(days));
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_DATE_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime, DateTimeFormatter dateTimeFormatter) {
        return dateTime.format(dateTimeFormatter);
    }

    public static String formatYear(String year, DateTimeFormatter formatter) {
        Year y = Year.parse(year);
        return y.format(formatter);
    }

    ////////////////////////

    public static List<String> getQuarterEndDates(List<String> dates) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<LocalDate> localDates = dates.stream()
                .map(date -> LocalDate.parse(date, formatter))
                .collect(Collectors.toList());

        // 분기별 마지막 영업일을 찾기 위해 분기별로 날짜를 그룹화합니다.
        Map<Integer, List<LocalDate>> groupedByQuarter = localDates.stream()
                .collect(Collectors.groupingBy(DateUtil::getQuarter));

        List<LocalDate> quarterEndDates = new ArrayList<>();

        // 각 분기별로 가장 마지막 날짜를 선택합니다.
        for (Map.Entry<Integer, List<LocalDate>> entry : groupedByQuarter.entrySet()) {
            List<LocalDate> quarterDates = entry.getValue();
            LocalDate latestDate = quarterDates.stream().max(LocalDate::compareTo).orElse(null);
            if (latestDate != null) {
                quarterEndDates.add(latestDate);
            }
        }

        // 날짜들을 다시 String으로 변환하여 반환합니다.
        return quarterEndDates.stream()
                .map(date -> date.format(formatter))
                .sorted() // 정렬하여 반환
                .collect(Collectors.toList());
    }

    // 주어진 날짜 리스트 중에서 특정 날짜 이전 또는 해당 날짜의 가장 가까운 날짜 찾기
    private static int getQuarter(LocalDate date) {
        int month = date.getMonthValue();
        if (month >= 1 && month <= 3) {
            return 1; // 1분기
        } else if (month >= 4 && month <= 6) {
            return 2; // 2분기
        } else if (month >= 7 && month <= 9) {
            return 3; // 3분기
        } else {
            return 4; // 4분기
        }
    }

    public static String getLatestFinancialStatementYear(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        int currentYear = LocalDateTime.now().getYear();

        // 올해인지 확인 후, 작년의 연도 반환
        if (dateTime.getYear() == currentYear) {
            return String.valueOf(currentYear - 1);
        } else {
            return String.valueOf(dateTime.getYear());
        }
    }

}
