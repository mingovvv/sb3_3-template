package demo.template.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static demo.template.common.utils.DateUtil.getQuarterEndDates;
import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void test1() {
//        List<String> strings = List.of("20230102", "20230105", "20230301", "20230501");
        List<String> strings = Arrays.asList("20230329", "20230330", "20230629", "20230630", "20230928", "20230929", "20231228", "20231229");

        System.out.println(getQuarterEndDates(strings));

    }


}