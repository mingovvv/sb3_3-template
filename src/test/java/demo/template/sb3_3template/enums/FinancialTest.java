package demo.template.sb3_3template.enums;

import demo.template.sb3_3template.entity.fs.Fs;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialTest {

    @Test
    void test1() {
        Fs previous = new Fs(null, "1", "1", "1", "1", "1", "111", "2023", "4", new BigDecimal("100.0000"), 14.2);
        Fs current = new Fs(null, "1", "1", "1", "1", "1", "111", "2024", "4", new BigDecimal("130.0000"), 24.2);

        System.out.println(FinancialClassifier.calculateYoYGrowthRate(true, previous, current));
        System.out.println(FinancialClassifier.calculateYoYGrowthRate(false, previous, current));
    }

    @Test
    void test2() {
        List<Fs> fs = List.of(
                new Fs(null, "1", "1", "1", "1", "1", "111", "202411", "3", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202410", "3", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202409", "2", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202408", "2", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202407", "2", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202403", "1", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202403", "1", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202402", "1", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202401", "1", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202311", "4", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202310", "4", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202309", "3", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202307", "3", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202306", "2", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202305", "2", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202304", "2", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202303", "1", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202302", "1", new BigDecimal("100.0000"), 14.2),
                new Fs(null, "1", "1", "1", "1", "1", "111", "202301", "1", new BigDecimal("100.0000"), 14.2)
        );

        List<Fs> latestFsPerQuarter = fs.stream()
                .collect(Collectors.groupingBy(fss -> fss.getFsDt().substring(0, 4) + "-" + fss.getQuarter())) // 연도와 quarter별로 그룹화
                .entrySet().stream()
                .flatMap(entry -> entry.getValue().stream() // 각 그룹에서
                        .max(Comparator.comparing(f -> YearMonth.parse(f.getFsDt(), DateTimeFormatter.ofPattern("yyyyMM")))) // fsDt를 LocalDate로 변환하여 비교
                        .stream())
                .sorted(Comparator
                        .comparing((Fs fss) -> YearMonth.parse(fss.getFsDt(), DateTimeFormatter.ofPattern("yyyyMM"))).reversed() // fsDt 내림차순 정렬
                        .thenComparing(Fs::getQuarter, Comparator.reverseOrder()) // quarter 내림차순 정렬
                )
                .toList();

        latestFsPerQuarter.forEach(fsss ->
                System.out.println("fsDt: " + fsss.getFsDt() + ", Quarter: " + fsss.getQuarter())
        );


    }

}
