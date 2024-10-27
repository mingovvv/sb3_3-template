package demo.template.sb3_3template.enums;

import demo.template.sb3_3template.dto.widget.ItemRateValDto;
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
        Integer minOriginValue = indicator.rawSeries;

        for (int i = 0; i < indicator.sequentialMonth - 1; i++) {
            result.add(new MonthlyTrend(dtoList.get(i).yearMonth(), TrendDirection.NOT_AVAILABLE));
        }

        for (int i = indicator.sequentialMonth - 1; i < dtoList.size(); i++) {

            boolean up = true;
            boolean down = true;

            for (int j = 0; j < indicator.sequentialMonth ; j++) {

                double preMRate = 0.0;
                if (i - j - 1 >= 0) {
                    preMRate = dtoList.get(i - j - 1).mRate();
                }

                ItemRateValDto current = dtoList.get(i - j);
                double mRate = current.mRate();

                if (mRate <= preMRate || (minOriginValue != null && Integer.parseInt(current.val()) < minOriginValue)) {
                    up = false;
                }

                if (mRate >= preMRate || (minOriginValue != null && Integer.parseInt(current.val()) > minOriginValue)) {
                    down = false;
                }

            }

            MonthlyTrend monthlyTrend = new MonthlyTrend(dtoList.get(i).yearMonth(), TrendDirection.FLAT);
            if (up) {
                monthlyTrend = new MonthlyTrend(dtoList.get(i).yearMonth(), TrendDirection.UP);
            } else if (down) {
                monthlyTrend = new MonthlyTrend(dtoList.get(i).yearMonth(), TrendDirection.DOWN);
            }

            result.add(monthlyTrend);

        }

        return result;


    }

    public static double[] calculateMatchRatios(List<MonthlyTrend> list1, List<MonthlyTrend> list2) {

        int upMatchCount = 0;
        int downMatchCount = 0;
        int totalCount = list1.size();

        for (int i = 0; i < totalCount; i++) {
            TrendDirection direction1 = list1.get(i).direction();
            TrendDirection direction2 = list2.get(i).direction();

            if (direction1 == TrendDirection.UP && direction2 == TrendDirection.UP) {
                upMatchCount++;
            } else if (direction1 == TrendDirection.DOWN && direction2 == TrendDirection.DOWN) {
                downMatchCount++;
            }
        }

        double upMatchRatio = (double) upMatchCount / totalCount;
        double downMatchRatio = (double) downMatchCount / totalCount;

        return new double[] { upMatchRatio, downMatchRatio, upMatchCount, downMatchCount };
    }

}
