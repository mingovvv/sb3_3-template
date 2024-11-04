package demo.template.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest {

    @Test
    void test() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("20240101", 1000);
//        map.put("20240102", 2000);
//        map.put("20240103", 3000);
//        map.put("20240105", 4000);
//        map.put("20240106", 5000);

        System.out.println(findPrice(map, "20240105", false));


    }

    public static Integer findPrice(TreeMap<String, Integer> map, String dateStr) {
        // 정확한 날짜가 존재하면 해당 날짜의 가격을 반환
        if (map.containsKey(dateStr)) {
            return map.get(dateStr);
        }

        // 정확한 날짜가 없으면 가장 가까운 날짜의 가격을 찾음
        Map.Entry<String, Integer> higherEntry = map.higherEntry(dateStr); // 이후 날짜 중 가장 가까운 날짜
        Map.Entry<String, Integer> lowerEntry = map.lowerEntry(dateStr);   // 이전 날짜 중 가장 가까운 날짜

        // 가장 가까운 날짜 찾기
        if (higherEntry == null && lowerEntry == null) {
            // 이후와 이전 날짜 모두 없으면 null 반환
            return null;
        } else if (higherEntry == null) {
            // 이후 날짜가 없으면 이전 날짜의 가격 반환
            return lowerEntry.getValue();
        } else if (lowerEntry == null) {
            // 이전 날짜가 없으면 이후 날짜의 가격 반환
            return higherEntry.getValue();
        } else {
            // 두 날짜가 모두 있는 경우, 더 가까운 날짜의 가격 반환
            long diffToLower = Math.abs(Long.parseLong(dateStr) - Long.parseLong(lowerEntry.getKey()));
            long diffToHigher = Math.abs(Long.parseLong(higherEntry.getKey()) - Long.parseLong(dateStr));

            return diffToLower <= diffToHigher ? lowerEntry.getValue() : higherEntry.getValue();
        }
    }

    public static Integer findPrice(TreeMap<String, Integer> map, String dateStr, boolean preferEarlier) {
        // 정확한 날짜가 존재하면 해당 날짜의 가격을 반환
        if (map.containsKey(dateStr)) {
            return map.get(dateStr);
        }

        // 정확한 날짜가 없으면 가장 가까운 날짜의 가격을 찾음
        Map.Entry<String, Integer> higherEntry = map.higherEntry(dateStr); // 이후 날짜 중 가장 가까운 날짜
        Map.Entry<String, Integer> lowerEntry = map.lowerEntry(dateStr);   // 이전 날짜 중 가장 가까운 날짜

        // "이전 날짜" 또는 "이후 날짜" 중 한 방향만 고려할 경우
        if (preferEarlier) {
            // 이전 날짜를 우선할 경우 이전 날짜가 있으면 반환, 없으면 null 반환
            return (lowerEntry != null) ? lowerEntry.getValue() : null;
        } else {
            // 이후 날짜를 우선할 경우 이후 날짜가 있으면 반환, 없으면 null 반환
            return (higherEntry != null) ? higherEntry.getValue() : null;
        }
    }

}
