package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;

import java.util.List;

public interface WidgetGeneratorStrategy {

    WidgetGroup getGroup();

    WidgetResponse generate(WidgetCreationDto dto, List<Integer> list, boolean includeCommonWidget);

}
