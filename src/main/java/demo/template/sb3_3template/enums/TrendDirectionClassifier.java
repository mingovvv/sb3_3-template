package demo.template.sb3_3template.enums;

import demo.template.sb3_3template.dto.widget.ItemRateValDto;
import demo.template.sb3_3template.dto.widget.MatchRatios;
import demo.template.sb3_3template.dto.widget.MonthlyTrend;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum TrendDirectionClassifier {

    EI001("785885", "미국 CPI yoy", "MK_ECONOMY", 3, 40),
    EI002("780951", "미국 경제활동 참가율", "MK_ECONOMY", 3, null),
    EI003("68", "한국 기준금리", "MK_ECONOMY", 1, null),
    DEFAULT("", "", "", 1, null);

    private final String ecoCd;
    private final String ecoNm;
    private final String tagId;
    private final int sequentialMonth;
    private final Integer rawSeries;

    TrendDirectionClassifier(String ecoCd, String ecoNm, String tagId, int sequentialMonth, Integer rawSeries) {
        this.ecoCd = ecoCd;
        this.ecoNm = ecoNm;
        this.tagId = tagId;
        this.sequentialMonth = sequentialMonth;
        this.rawSeries = rawSeries;
    }

    private static final Map<String, TrendDirectionClassifier> INDICATOR_BY_ECOCODE_MAP;

    static {
        INDICATOR_BY_ECOCODE_MAP = Stream.of(TrendDirectionClassifier.values())
                .collect(Collectors.toMap(TrendDirectionClassifier::getEcoCd, Function.identity()));
    }

    public static TrendDirectionClassifier findByItemCd(String itemCd) {
        return INDICATOR_BY_ECOCODE_MAP.getOrDefault(itemCd, DEFAULT);
    }

    public static List<MonthlyTrend> analyzeTrendDirection(String itemCd, List<ItemRateValDto> dtoList) {

        List<MonthlyTrend> result = new ArrayList<>();

        TrendDirectionClassifier indicator = INDICATOR_BY_ECOCODE_MAP.getOrDefault(itemCd, DEFAULT);
        int sequentialMonth = indicator.sequentialMonth;
        Integer minOriginValue = indicator.rawSeries;

        if (dtoList.size() < indicator.sequentialMonth) {
            return result; // 또는 적절한 에러 처리
        }

        // 첫 번째 sequentialMonth - 1개의 데이터는 항상 NOT_AVAILABLE 설정
        for (int i = 0; i < sequentialMonth; i++) {
            result.add(new MonthlyTrend(dtoList.get(i).yearMonth(), TrendDirection.NOT_AVAILABLE, dtoList.get(i).mRate()));
        }

        // 데이터가 충분하지 않은 경우 처리
        if (dtoList.size() < sequentialMonth) {
            return result; // 데이터가 부족할 경우, 이미 채운 NOT_AVAILABLE만 반환
        }

        for (int i = sequentialMonth; i < dtoList.size(); i++) {

            boolean up = true;
            boolean down = true;

            // 이전 sequentialMonth 개월의 데이터를 비교
            for (int j = 0; j < indicator.sequentialMonth ; j++) {

                ItemRateValDto preDto = dtoList.get(i - j - 1);
                ItemRateValDto currentDto = dtoList.get(i - j);


                if (currentDto.mRate() <= preDto.mRate() || (minOriginValue != null && Integer.parseInt(currentDto.val()) < minOriginValue)) {
                    up = false;
                }

                if (currentDto.mRate() >= preDto.mRate() || (minOriginValue != null && Integer.parseInt(currentDto.val()) > minOriginValue)) {
                    down = false;
                }

            }

            // 트렌드 결정
            TrendDirection trendDirection = up ? TrendDirection.UP : down ? TrendDirection.DOWN : TrendDirection.FLAT;

            result.add(new MonthlyTrend(dtoList.get(i).yearMonth(), trendDirection, dtoList.get(i).mRate()));

        }

        return result;


    }

    public static MatchRatios calculateMatchRatios(List<MonthlyTrend> list1, List<MonthlyTrend> list2) {

        int upMatchCount = 0;
        int downMatchCount = 0;
        int totalUpCount = 0;
        int totalDownCount = 0;

        // target
        Map<String, TrendDirection> targetMap = list2.stream()
                .collect(Collectors.toMap(MonthlyTrend::yearMonth, MonthlyTrend::direction));

        for (MonthlyTrend sourceMonthlyTrend : list1) {
            TrendDirection sourceDirection = sourceMonthlyTrend.direction();
            TrendDirection targetDirection = targetMap.getOrDefault(sourceMonthlyTrend.yearMonth(), TrendDirection.FLAT);

            if (sourceDirection == TrendDirection.UP) {
                totalUpCount++;
                if (targetDirection == TrendDirection.UP) {
                    upMatchCount++;
                }
            } else if (sourceDirection == TrendDirection.DOWN) {
                totalDownCount++;
                if (targetDirection == TrendDirection.DOWN) {
                    downMatchCount++;
                }
            }
        }

        // 상승 및 하락 매칭 비율 계산
        double upMatchRatio = totalUpCount > 0 ? Math.round((double) upMatchCount / totalUpCount * 1000) / 10.0 : 0.0;
        double downMatchRatio = totalDownCount > 0 ? Math.round((double) downMatchCount / totalDownCount * 1000) / 10.0 : 0.0;
        double avgMonthlyReturnWhenUp = Math.round(list2.stream().filter(s -> s.direction() == TrendDirection.UP).mapToDouble(MonthlyTrend::mRate).average().orElse(0.0) * 10) / 10.0;
        double avgMonthlyReturnWhenDown = Math.round(list2.stream().filter(s -> s.direction() == TrendDirection.DOWN).mapToDouble(MonthlyTrend::mRate).average().orElse(0.0 * 10)) / 10.0;

        return new MatchRatios(upMatchRatio, downMatchRatio, upMatchCount, downMatchCount, avgMonthlyReturnWhenUp, avgMonthlyReturnWhenDown);
    }

}
