package demo.template.sb3_3template.enums;

import demo.template.sb3_3template.entity.fs.Fs;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FinancialTest {

    @Test
    void test1() {
        Fs previous = new Fs(null, "1", "1", "1", "1", "1", "111", "2023", "4", new BigDecimal("100.0000"), 14.2);
        Fs current = new Fs(null, "1", "1", "1", "1", "1", "111", "2024", "4", new BigDecimal("130.0000"), 24.2);

        System.out.println(FinancialClassifier.calculateYoYGrowthRate(true, previous, current));
        System.out.println(FinancialClassifier.calculateYoYGrowthRate(false, previous, current));
    }

}
