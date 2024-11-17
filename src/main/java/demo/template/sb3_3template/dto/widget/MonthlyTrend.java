package demo.template.sb3_3template.dto.widget;

import demo.template.sb3_3template.enums.TrendDirection;

public record MonthlyTrend(

        String yearMonth,
        TrendDirection direction,
        double mRate

) {

}
