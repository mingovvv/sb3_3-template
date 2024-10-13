package demo.template.sb3_3template.service;

import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;

import java.util.List;

public class WidgetObjCreator {

    public static WidgetResponse.Widget createWidget1Object(WidgetGroup.Widget widget5, String template, String... templateValues) {

        var val1 = templateValues[0] == null? "많은": "적은";
        var val2 = Integer.parseInt(templateValues[1]) > 6 ? "해외": "국내";

        return WidgetResponse.Widget.builder()
                .widgetNo(widget5.getWNo())
                .template(template)
                .values(List.of(
//                        WidgetResponse.Widget.Values.builder().text(val1).color("yellow").isBold(true).build(),
//                        WidgetResponse.Widget.Values.builder().text(val2).color("red").isBold(true).build()
                ))
                .build();
    }

    public static WidgetResponse.Widget createWidget2Object(WidgetGroup.Widget widget5, String template, String... templateValues) {

        var val1 = templateValues[0] == null? "많습니다.": "적습니다.";
        var val2 = Integer.parseInt(templateValues[1]) > 11100 ? "비쌉니다.": "저렴합니다.";

        return WidgetResponse.Widget.builder()
                .widgetNo(widget5.getWNo())
                .template(template)
                .values(List.of(
//                        WidgetResponse.Widget.Values.builder().text(val1).color("yellow").isBold(true).build(),
//                        WidgetResponse.Widget.Values.builder().text(val2).color("red").isBold(true).build()
                ))
                .build();
    }

    public static WidgetResponse.Widget createWidget3Object(WidgetGroup.Widget widget5, String template, String... templateValues) {
        var val1 = templateValues[0] == null? "기회입니다.": "기회가 아닙니다.";

        return WidgetResponse.Widget.builder()
                .widgetNo(widget5.getWNo())
                .template(template)
                .values(List.of(
//                        WidgetResponse.Widget.Values.builder().text(val1).color("yellow").isBold(true).build()
                ))
                .build();
    }

}
