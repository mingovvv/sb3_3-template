package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWidgetGenerator implements WidgetGeneratorStrategy {

    @Override
    public abstract WidgetGroup getGroup();


    @Override
    public WidgetResponse generate(Object object1, List<Integer> list, boolean includeCommonWidget) {

        WidgetResponse response = new WidgetResponse(1, null, new ArrayList<>());

        // 공통 위젯 조건이 만족되면 포함
        if (includeCommonWidget) {
            response.getWidgets().add(generateCommonWidget());
        }

        // 그룹에 속한 위젯들 생성
        list.forEach(widgetNo -> {
            response.getWidgets().add(generateSpecificWidget(widgetNo));
        });

        return response;
    }

    // 공통 위젯 생성
    private WidgetResponse.Widget generateCommonWidget() {
        // 공통 위젯 생성 로직
        return null;
    }

    // 구체적인 위젯 생성 로직을 각 하위 클래스에서 구현
    protected abstract WidgetResponse.Widget generateSpecificWidget(int widgetNo);

}
