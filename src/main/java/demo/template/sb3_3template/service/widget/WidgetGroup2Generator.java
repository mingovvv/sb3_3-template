package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WidgetGroup2Generator implements WidgetGeneratorStrategy {

    // repo

    @Override
    public WidgetGroup getGroup() {
        return WidgetGroup.WIDGET_GROUP_2;
    }

    @Override
    public WidgetResponse generate(WidgetCreationDto dto, List<Integer> list, boolean includeCommonWidget) {

        // widgetGroup 2 생성
        return null;

    }

}
