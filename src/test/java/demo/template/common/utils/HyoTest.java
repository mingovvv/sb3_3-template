package demo.template.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HyoTest {

    @Test
    void test() {

        List<Data> dataList = Arrays.asList(
                new Data(1, 1001, 2001, "LU001", "PARTICLE", "GIS", -30.5, -40.2, 85.3, "/data/path1", "graph1", "admin", "user1", LocalDate.now().atTime(9, 0), LocalDateTime.now()),
                new Data(2, 1002, 2001, "LU001", "PARTICLE", "GIS", -28.3, -38.7, 90.1, "/data/path2", "graph2", "admin", "user2", LocalDate.now().atTime(10, 0), LocalDateTime.now()),
                new Data(3, 1003, 2001, "LU001", "PARTICLE", "GIS", -25.7, -35.5, 92.8, "/data/path3", "graph3", "admin", "user3", LocalDate.now().atTime(11, 0), LocalDateTime.now()),
                new Data(3, 1003, 2002, "LU001", "PARTICLE", "GIS", -25.7, -35.5, 92.8, "/data/path3", "graph3", "admin", "user3", LocalDate.now().atTime(12, 0), LocalDateTime.now()),
                new Data(3, 1003, 2002, "LU001", "PARTICLE", "GIS", -25.7, -35.5, 92.8, "/data/path3", "graph3", "admin", "user3", LocalDate.now().atTime(13, 0), LocalDateTime.now()),
                new Data(3, 1003, 2002, "LU001", "PARTICLE", "GIS", -25.7, -35.5, 92.8, "/data/path3", "graph3", "admin", "user3", LocalDate.now().atTime(14, 0), LocalDateTime.now()),
                new Data(3, 1003, 2003, "LU001", "PARTICLE", "GIS", -25.7, -35.5, 92.8, "/data/path3", "graph3", "admin", "user3", LocalDate.now().atTime(15, 0), LocalDateTime.now()),
                new Data(3, 1003, 2004, "LU001", "PARTICLE", "GIS", -25.7, -35.5, 92.8, "/data/path3", "graph3", "admin", "user3", LocalDate.now().atTime(16, 0), LocalDateTime.now())
        );

        Map<Integer, Map<Integer, Long>> collect = dataList.stream().collect(
                Collectors.groupingBy(Data::getChId,
                        Collectors.groupingBy(
                                data2 -> data2.getCrtDt().getHour(),
                                Collectors.counting()
                        )));

        Map<Integer, Map<Integer, Long>> groupedData = dataList.stream()
                .collect(Collectors.groupingBy(
                        Data::getChId,
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        data -> data.getCrtDt().getHour(),
                                        Collectors.counting()
                                ),
                                hourMap -> {
                                    // 0시부터 23시까지 없는 시간은 0건으로 추가
                                    for (int hour = 0; hour < 24; hour++) {
                                        hourMap.putIfAbsent(hour, 0L);
                                    }
                                    return hourMap;
                                }
                        )
                ));

//        Map<LocalDate, Map<Integer, Map<Integer, Long>>> groupedData2 = dataList.stream()
//                .collect(Collectors.groupingBy(
//                        record -> record.getCrtDt().toLocalDate(),  // 날짜별 그룹핑
//                        Collectors.groupingBy(
//                                Data::getChId,  // 채널 ID별 그룹핑
//                                Collectors.collectingAndThen(
//                                        Collectors.groupingBy(
//                                                record -> record.getCrtDt().getHour(), // 시간(HH)별 그룹핑
//                                                Collectors.counting()
//                                        ),
//                                        hourMap -> {
//                                            // 0시부터 23시까지 없는 시간은 0건 추가
//                                            for (int hour = 0; hour < 24; hour++) {
//                                                hourMap.putIfAbsent(hour, 0L);
//                                            }
//                                            return hourMap;
//                                        }
//                                )
//                        )
//                ));

//        List<Data1DepthRes> data1DepthRes = groupData(dataList);

        List<Data1DepthRes> data1DepthRes = Data1DepthRes.groupedCountByDateAndChIdAndHour(dataList);

//        System.out.println(collect);
//        System.out.println(groupedData);
//        System.out.println(groupedData2);
        System.out.println(data1DepthRes);


    }


    @Getter
    @AllArgsConstructor
    public static class Data {
        private int seq; // 시퀀스
        private int dauId; // 변전소 ID
        private int chId; // 채널 ID
        private String luId; // 설비 ID
        private String sts; // 진단결과, 판정결과
        private String typ; // 부분방전 타입
        private double maxd; // 최대신호크기(dBm)
        private double avgd; // 평균신호크기(dBm)
        private double pps; // PPS(%)
        private String datPath; // 데이터 경로
        private String grph; // 그래프 데이터
        private String crtr; // 생성자
        private String uptr; // 수정자
        private LocalDateTime crtDt; // 생성일시
        private LocalDateTime uptDt; // 수정일시
    }

    public static List<Data1DepthRes> groupData(List<Data> dataList) {

        // 1단계: Data를 날짜별로 그룹핑 (crtDt의 날짜 부분)
        Map<String, List<Data>> groupedByDate = groupedByDate(dataList);

        // 각 날짜별로 채널 및 시간 그룹핑 처리
        return groupedByDate.entrySet().stream()
                .map(dateEntry -> {
                    String date = dateEntry.getKey();
                    List<Data> listByDate = dateEntry.getValue();

                    // 2단계: 같은 날짜 내에서 채널별로 그룹핑 (chId)
                    Map<String, List<Data>> groupedByChId = listByDate.stream()
                            .collect(Collectors.groupingBy(data -> String.valueOf(data.getChId())));

                    // 각 채널 그룹별로 시간별 카운팅 처리
                    List<Data1DepthRes.Data2DepthRes> dep2List = groupedByChId.entrySet().stream()
                            .map(chEntry -> {
                                String chId = chEntry.getKey();
                                List<Data> listByChId = chEntry.getValue();

                                // 3단계: 같은 날짜, 채널 내에서 시간(시)별 그룹핑 (crtDt의 hour)
                                Map<Integer, Long> groupedByHour = listByChId.stream()
                                        .collect(Collectors.groupingBy(data -> data.getCrtDt().getHour(), Collectors.counting()));

                                // 0시부터 23시까지 모두 포함해서 Data3DepthRes 생성
                                List<Data1DepthRes.Data2DepthRes.Data3DepthRes> dep3List =
                                        IntStream.range(0, 24)
                                                .mapToObj(hour -> new Data1DepthRes.Data2DepthRes.Data3DepthRes(
                                                        hour,
                                                        groupedByHour.getOrDefault(hour, 0L).intValue()))
                                                .collect(Collectors.toList());

                                return new Data1DepthRes.Data2DepthRes(chId, dep3List, dep3List.stream().mapToInt(Data1DepthRes.Data2DepthRes.Data3DepthRes::count).sum());
                            })
                            .collect(Collectors.toList());

                    return new Data1DepthRes(date, dep2List);
                })
                .collect(Collectors.toList());
    }

    /**
     * 날짜별로 그룹핑
     */
    public static Map<String, List<Data>> groupedByDate(List<Data> dataList) {
        // 날짜 포맷 정의 (필요하면 변경해)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dataList.stream()
                .collect(Collectors.groupingBy(data -> data.getCrtDt().format(dateFormatter)));
    }

    /**
     * ChId별로 그룹핑
     */
    public static Map<Integer, List<Data>> groupedByChId(List<Data> dataList) {
        return dataList.stream()
                .collect(Collectors.groupingBy(Data::getChId));
    }

    /**
     * 시간별로 그룹핑
     */
    public static Map<Integer, List<Data>> groupedByHour(List<Data> dataList) {
        return dataList.stream()
                .collect(Collectors.groupingBy(data -> data.getCrtDt().getHour()));
    }

    /**
     * 모든 그룹핑 작업을 스트림과 of 메소드를 활용하여 처리.
     * 날짜 -> 채널 -> 시간(0~23시) 순으로 그룹핑하며, 각 시간대에 데이터가 없으면 count 0으로 채움.
     */
    public static List<Data1DepthRes> groupedCountByDateAndChIdAndHour2(List<Data> dataList) {
        return groupedByDate(dataList).entrySet().stream()
                .map(dateEntry -> {
                    String date = dateEntry.getKey();
                    List<Data> dateList = dateEntry.getValue();

                    List<Data1DepthRes.Data2DepthRes> dep2List = groupedByChId(dateList).entrySet().stream()
                            .map(chEntry -> {
                                String chId = String.valueOf(chEntry.getKey());
                                List<Data> chList = chEntry.getValue();

                                Map<Integer, List<Data>> hourGrouped = groupedByHour(chList);

                                List<Data1DepthRes.Data2DepthRes.Data3DepthRes> dep3List =
                                        IntStream.range(0, 24)
                                                .mapToObj(hour -> Data1DepthRes.Data2DepthRes.Data3DepthRes.of(
                                                        hour,
                                                        hourGrouped.getOrDefault(hour, Collections.emptyList()).size()
                                                ))
                                                .collect(Collectors.toList());
                                return Data1DepthRes.Data2DepthRes.of(chId, dep3List);
                            })
                            .collect(Collectors.toList());
                    return Data1DepthRes.of(date, dep2List);
                })
                .collect(Collectors.toList());
    }

}
