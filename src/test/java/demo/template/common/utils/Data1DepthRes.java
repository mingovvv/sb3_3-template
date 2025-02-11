package demo.template.common.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static demo.template.common.utils.HyoTest.*;

public record Data1DepthRes(
        String date,
        List<Data2DepthRes> dep2
) {

    public record Data2DepthRes(
            String chId,
            List<Data3DepthRes> dep3,
            int totalCount
    ) {

        public record Data3DepthRes(
                int hour,
                int count
        ) {

            public static Data3DepthRes of(int hour, int count) {
                return new Data3DepthRes(hour, count);
            }

        }

        public static Data2DepthRes of(String chId, List<Data3DepthRes> dep3) {
            return new Data2DepthRes(chId, dep3, dep3.stream().mapToInt(Data3DepthRes::count).sum());
        }

        // 채널별 응답 객체 생성
        private static Data1DepthRes.Data2DepthRes buildData2DepthRes(String chId, List<Data> chData) {
            Map<Integer, List<Data>> hourGrouped = groupedByHour(chData);
            List<Data1DepthRes.Data2DepthRes.Data3DepthRes> dep3List =
                    IntStream.range(0, 24)
                            .mapToObj(hour -> Data1DepthRes.Data2DepthRes.Data3DepthRes.of(
                                    hour,
                                    hourGrouped.getOrDefault(hour, Collections.emptyList()).size()
                            ))
                            .collect(Collectors.toList());
            return Data1DepthRes.Data2DepthRes.of(chId, dep3List);
        }

    }

    public static Data1DepthRes of(String date, List<Data1DepthRes.Data2DepthRes> dep2List) {
        return new Data1DepthRes(date, dep2List);
    }

    // 날짜별 응답 객체 생성
    private static Data1DepthRes buildData1DepthRes(String date, List<Data> dateData) {
        List<Data1DepthRes.Data2DepthRes> dep2List = groupedByChId(dateData).entrySet().stream()
                .map(entry -> Data1DepthRes.Data2DepthRes.buildData2DepthRes(String.valueOf(entry.getKey()), entry.getValue()))
                .toList();
        return Data1DepthRes.of(date, dep2List);
    }


    // 전체 결과를 만드는 메소드 - 메소드 내부의 각 단계별 작업을 별도 메소드로 분리
    public static List<Data1DepthRes> groupedCountByDateAndChIdAndHour(List<HyoTest.Data> dataList) {
        return groupedByDate(dataList).entrySet().stream()
                .map(entry -> buildData1DepthRes(entry.getKey(), entry.getValue()))
                .toList();
    }

}
