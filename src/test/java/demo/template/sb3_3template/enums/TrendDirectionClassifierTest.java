package demo.template.sb3_3template.enums;

import demo.template.sb3_3template.dto.widget.ItemRateValDto;
import org.junit.jupiter.api.Test;

import java.util.List;

class TrendDirectionClassifierTest {

    @Test
    void analyzeTrendDirection() {
        List<ItemRateValDto> itemRateValDtos = List.of(
                new ItemRateValDto("TEST001", "삼전", "202401", 1.4, "40"),
                new ItemRateValDto("TEST001", "삼전", "202402", 1.1, "40"),
                new ItemRateValDto("TEST001", "삼전", "202403", 1.2, "40"),
                new ItemRateValDto("TEST001", "삼전", "202404", 1.4, "40"),
                new ItemRateValDto("TEST001", "삼전", "202405", 1.5, "40")
        );

        System.out.println(TrendDirectionClassifier.analyzeTrendDirection("785885", itemRateValDtos));


    }


}