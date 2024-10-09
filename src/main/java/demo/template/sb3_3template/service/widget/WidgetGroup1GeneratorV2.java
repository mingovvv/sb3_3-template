package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;
import org.springframework.stereotype.Service;

@Service
public class WidgetGroup1GeneratorV2 extends AbstractWidgetGenerator {

    @Override
    public WidgetGroup getGroup() {
        return WidgetGroup.WIDGET_GROUP_1;
    }

    @Override
    protected WidgetResponse.Widget generateSpecificWidget(int widgetNo) {
        return switch (widgetNo) {
            case 5 -> generateWidget5();
            case 6 -> generateWidget6();
            case 7 -> generateWidget7();
            case 8 -> generateWidget8();
            default -> throw new IllegalStateException("Unexpected value: " + widgetNo);
        };
    }

//    private final WidgetResponse widgetResponse;
//    private final Map<Integer, Supplier<WidgetResponse.Widget>> widgetMap = new HashMap<>();
//
//    // repo
//
//    public WidgetGroup1GeneratorUpgrade() {
//        widgetResponse = new WidgetResponse(1, new ArrayList<>(), new ArrayList<>());
//        widgetMap.put(5, this::generateWidget5);
//        widgetMap.put(6, this::generateWidget6);
//        widgetMap.put(7, this::generateWidget7);
//        widgetMap.put(8, this::generateWidget8);
//    }
//
//    @Override
//    public WidgetGroup getGroup() {
//        return WidgetGroup.WIDGET_GROUP_1;
//    }
//
//    @Override
//    public WidgetResponse generate(Object object1, List<Integer> list) {
//
//        list.forEach(widgetNo -> {
//            Supplier<WidgetResponse.Widget> widgetSupplier = widgetMap.get(widgetNo);
//            widgetResponse.getWidgets().add(widgetSupplier.get());
//        });
//
//        return widgetResponse;
//
//    }

    private WidgetResponse.Widget generateWidget5() {
        // 위젯 5 생성 로직
        return null;
    }

    private WidgetResponse.Widget generateWidget6() {
        // 위젯 6 생성 로직
        return null;
    }

    private WidgetResponse.Widget generateWidget7() {
        // 위젯 7 생성 로직
        return null;
    }

    private WidgetResponse.Widget generateWidget8() {
        // 위젯 8 생성 로직
        return null;
    }


}
