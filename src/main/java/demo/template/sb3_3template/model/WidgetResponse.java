package demo.template.sb3_3template.model;

import lombok.Getter;

@Getter
public class WidgetResponse<T> {

    private int widgetGroupNo;
    private T data;

    public WidgetResponse(int widgetGroupNo, T data) {
        this.widgetGroupNo = widgetGroupNo;
        this.data = data;
    }

}
