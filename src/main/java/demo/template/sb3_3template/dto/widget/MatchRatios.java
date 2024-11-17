package demo.template.sb3_3template.dto.widget;

public record MatchRatios(
        // 동반 상승 일치율
        double upMatchRatio,
        // 동반 하락 일치율
        double downMatchRatio,
        // 동반 상승 횟수
        int upMatchCount,
        // 동반 하락 횟수
        int downMatchCount,
        // 동반 상승 시, 타겟 월평균 수익율
        double avgMonthlyReturnWhenUp,
        // 동반 하락 시, 타겟 월평균 수익율
        double avgMonthlyReturnWhenDown
) {
}
