package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;

import java.util.List;

public interface WidgetGeneratorStrategy {

    WidgetGroup getGroup();

    WidgetResponse generate(Object object1, List<Integer> list, boolean includeCommonWidget);

}
