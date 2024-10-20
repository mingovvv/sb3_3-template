package demo.template.sb3_3template.service.widget;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class WG3Test {

    @Test
    void w11() {
        List<W3ReturnMProjection> source = new ArrayList<>();
        List<W3ReturnMProjection> target = new ArrayList<>();

        source.addAll(

                List.of(
                        new W3ReturnMProjection("item1", "202401", 1.0, 5),
                        new W3ReturnMProjection("item1", "202402", 1.5, 5),
                        new W3ReturnMProjection("item1", "202403", 2.0, 5),
                        new W3ReturnMProjection("item1", "202404", -1.1, 5),
                        new W3ReturnMProjection("item1", "202405", 1.6, 5),
                        new W3ReturnMProjection("item1", "202406", 1.6, 5),
                        new W3ReturnMProjection("item1", "202407", 1.6, 5),
                        new W3ReturnMProjection("item1", "202408", 1.6, 5),
                        new W3ReturnMProjection("item1", "202409", 1.6, 5),
                        new W3ReturnMProjection("item1", "202410", 1.6, 5),
                        new W3ReturnMProjection("item1", "202411", 1.6, 5),
                        new W3ReturnMProjection("item1", "202412", 1.6, 5)
                )

        );

        target.addAll(

                List.of(
                        new W3ReturnMProjection("item1", "202401", 1.0, 5),
                        new W3ReturnMProjection("item1", "202402", 1.5, 5),
                        new W3ReturnMProjection("item1", "202403", 2.0, 5),
                        new W3ReturnMProjection("item1", "202404", -1.1, 5),
                        new W3ReturnMProjection("item1", "202405", 1.6, 5),
                        new W3ReturnMProjection("item1", "202406", 1.6, 5),
                        new W3ReturnMProjection("item1", "202407", 1.6, 5),
                        new W3ReturnMProjection("item1", "202408", 1.6, 5),
                        new W3ReturnMProjection("item1", "202409", 1.6, 5),
                        new W3ReturnMProjection("item1", "202410", 1.6, 5),
                        new W3ReturnMProjection("item1", "202411", 1.6, 5),
                        new W3ReturnMProjection("item1", "202412", 1.6, 5)
                )

        );

        int consecutiveMonths = 3; // 연속 검증 개월 수
        double thresholdDiff = 5; // 검증 차분값

        List<W12Dto> w12Dtos1 = calculateRates(source, consecutiveMonths, thresholdDiff);
        List<W12Dto> so = w12Dtos1.stream().filter(s -> Integer.parseInt(s.yearMonth) >= 202406).toList();

        List<W12Dto> w12Dtos2 = calculateRates(target, consecutiveMonths, thresholdDiff);
        List<W12Dto> ta = w12Dtos2.stream().filter(s -> Integer.parseInt(s.yearMonth) >= 202406).toList();


        calculateMatchRates(so, ta);


    }

    static class W3ReturnMProjection {
        String itemCd;
        String yearMonth;
        double returnRateM;
        double diff;
        public W3ReturnMProjection(String itemCd, String yearMonth, double returnRateM, double diff) {
            this.itemCd = itemCd;
            this.yearMonth = yearMonth;
            this.returnRateM = returnRateM;
            this.diff = diff;
        }
    }

    static class W3CloseProjection {
        String itemCd;
        String stdDt;
        String close;

        public W3CloseProjection(String itemCd, String stdDt, String close) {
            this.itemCd = itemCd;
            this.stdDt = stdDt;
            this.close = close;
        }
    }

    public static List<W12Dto> calculateRates(List<W3ReturnMProjection> source,
                                      int consecutiveMonths,
                                      double thresholdDiff) {

        List<W12Dto> objects = new ArrayList<>();

        int upCount = 0;
        int downCount = 0;
        int elseCount = 0;

        // 연속 상승 및 하락 여부 추적
        int continuousUp = 0;
        int continuousDown = 0;

        for (int i = consecutiveMonths - 1; i < source.size(); i++) {

            W3ReturnMProjection now = source.get(i);

            for (int j = i + 1 - consecutiveMonths; j <= i; j++) {

                W3ReturnMProjection obj = source.get(j);

                if (obj.returnRateM >= 0 && obj.diff >= thresholdDiff) {
                    continuousUp++;
                } else if (obj.returnRateM < 0 && obj.diff < thresholdDiff) {
                    continuousDown++;
                }

            }

            String movementStatus;

            if (continuousUp == consecutiveMonths) {
                movementStatus = "상승";
            } else if (continuousDown == consecutiveMonths) {
                movementStatus = "하락";
            } else {
                movementStatus = "그외";
            }

            // 초기화
            continuousUp = 0;
            continuousDown = 0;

            W12Dto w12Dto = new W12Dto(now.yearMonth, movementStatus, now.returnRateM);
            objects.add(w12Dto);
        }

        return objects;

    }

    public static void calculateMatchRates(List<W12Dto> source, List<W12Dto> target) {
//        if (source.size() != target.size()) {
//            throw new IllegalArgumentException("Source와 Target 리스트의 크기가 같아야 합니다.");
//        }
//
//        int total = source.size();
//        int upMatchCount = 0;
//        int downMatchCount = 0;
//
//        // 리스트를 순회하며 상승/하락 상태 비교
//        for (int i = 0; i < total; i++) {
//            String sourceStatus = source.get(i).movementStatus;
//            String targetStatus = target.get(i).movementStatus;
//
//            if (sourceStatus.equals("상승") && targetStatus.equals("상승")) {
//                upMatchCount++;
//            } else if (sourceStatus.equals("하락") && targetStatus.equals("하락")) {
//                downMatchCount++;
//            }
//        }
//
//        // 상승, 하락 일치 비율 계산
//        double upMatchRate = (double) upMatchCount / total * 100;
//        double downMatchRate = (double) downMatchCount / total * 100;
//
//        // 결과 출력
//        System.out.printf("상승 일치 비율: %.2f%%%n", upMatchRate);
//        System.out.printf("하락 일치 비율: %.2f%%%n", downMatchRate);

        if (source.size() != target.size()) {
            throw new IllegalArgumentException("Source와 Target 리스트의 크기가 같아야 합니다.");
        }

        int upTotalCount = 0; // "상승" 상태의 총 항목 수
        int downTotalCount = 0; // "하락" 상태의 총 항목 수
        int upMatchCount = 0; // "상승" 일치한 항목 수
        int downMatchCount = 0; // "하락" 일치한 항목 수

        // 리스트를 순회하며 상승/하락 상태 비교
        for (int i = 0; i < source.size(); i++) {
            String sourceStatus = source.get(i).movementStatus;
            String targetStatus = target.get(i).movementStatus;

            // "상승" 상태 비교
            if (sourceStatus.equals("상승")) {
                upTotalCount++; // "상승" 상태 총 카운트 증가
                if (targetStatus.equals("상승")) {
                    upMatchCount++; // "상승" 일치 카운트 증가
                }
            }

            // "하락" 상태 비교
            if (sourceStatus.equals("하락")) {
                downTotalCount++; // "하락" 상태 총 카운트 증가
                if (targetStatus.equals("하락")) {
                    downMatchCount++; // "하락" 일치 카운트 증가
                }
            }
        }

        // 상승, 하락 일치 비율 계산 (각각 "상승", "하락" 항목에 대해서만 비율을 계산)
        double upMatchRate = upTotalCount == 0 ? 0 : (double) upMatchCount / upTotalCount * 100;
        double downMatchRate = downTotalCount == 0 ? 0 : (double) downMatchCount / downTotalCount * 100;

        // 결과 출력
        System.out.printf("상승 일치 비율: %.2f%%%n", upMatchRate);
        System.out.printf("하락 일치 비율: %.2f%%%n", downMatchRate);

    }

    static class W12Dto{
        String yearMonth;
        String movementStatus;
        double returnRateM;

        public W12Dto(String yearMonth, String movementStatus, double returnRateM) {
            this.yearMonth = yearMonth;
            this.movementStatus = movementStatus;
            this.returnRateM = returnRateM;
        }

        @Override
        public String toString() {
            return "W12Dto{" +
                    "yearMonth='" + yearMonth + '\'' +
                    ", movementStatus='" + movementStatus + '\'' +
                    ", returnRateM=" + returnRateM +
                    '}';
        }
    }

}
