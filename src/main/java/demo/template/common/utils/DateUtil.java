package demo.template.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private DateUtil() {}

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String getMinusDay(int days) {
        return formatDateTime(now().minusDays(days));
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_DATE_FORMATTER);
    }

}
