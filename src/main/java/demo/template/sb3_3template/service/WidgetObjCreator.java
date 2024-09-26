package demo.template.sb3_3template.service;

import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;

import java.util.List;

public class WidgetObjCreator {

    public static WidgetResponse.Widget<?> createWidgetObject(WidgetGroup.Widget widget5, String template, String... templateValues) {
        return WidgetResponse.Widget.builder()
                .widgetNo(widget5.getWNo())
                .template(template)
                .values(List.of(
                        WidgetResponse.Widget.Values.builder().text(templateValues[0]).color("").isBold(true).build()
                ))
                .build();
    }

}
