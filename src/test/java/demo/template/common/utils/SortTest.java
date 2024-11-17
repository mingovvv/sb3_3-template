package demo.template.common.utils;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SortTest {

    @Test
    void test1(){

        Map<String, Map<Integer, Double>> dummy = Map.of("삼성전자", Map.of(1, 10.1, 7, 2.4, 30, 24.1),
                "LG전자", Map.of(1, 25.9, 7, -1.6, 30, 1.2),
                "SK하이닉스", Map.of(1, 120.24, 7, 21.4, 30, 10.1));

        Map<String, Map<Integer, Double>> sortedDummy = dummy.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    Double value1 = e1.getValue().getOrDefault(1, Double.NEGATIVE_INFINITY);
                    Double value2 = e2.getValue().getOrDefault(1, Double.NEGATIVE_INFINITY);
                    return value2.compareTo(value1);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        System.out.println(dummy);
        System.out.println(sortedDummy);


    }

}
