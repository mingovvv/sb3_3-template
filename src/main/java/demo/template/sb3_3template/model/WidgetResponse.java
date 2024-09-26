package demo.template.sb3_3template.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class WidgetResponse {

    private int widgetGroupNo;
    private List<String> entities;
    private List<Widget> widgets;

    public WidgetResponse(int widgetGroupNo, List<String> entities, List<Widget> widgets) {
        this.widgetGroupNo = widgetGroupNo;
        this.entities = entities;
        this.widgets = widgets;
    }

    static public class Widget<T> {
        private int widgetNo;
        private String template;
        private List<Values> values;
        private T data;

        @Builder
        public Widget(int widgetNo, String template, List<Values> values, T data) {
            this.widgetNo = widgetNo;
            this.template = template;
            this.values = values;
            this.data = data;
        }

        static public class Values {
            private String text;
            private String color;
            private boolean isBold;

            @Builder
            public Values(String text, String color, boolean isBold) {
                this.text = text;
                this.color = color;
                this.isBold = isBold;
            }

        }

    }

}
