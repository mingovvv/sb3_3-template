package demo.template.sb3_3template.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
public class WidgetResponse {

    private Long conversationId;
    private int widgetGroupNo;
    private List<Widget> widgets;

    @Builder
    public WidgetResponse(Long conversationId, int widgetGroupNo, List<Widget> widgets) {
        this.conversationId = conversationId;
        this.widgetGroupNo = widgetGroupNo;
        this.widgets = widgets;
    }

    @Getter
    static public class Widget {
        private Long widgetHistId;
        private Boolean isLiked;
        private Boolean isEvaluated;
        private int widgetNo;
        private String subject;
        private String template;
        private List<Values> values;
        private Supplement supplement;

        @Builder
        public Widget(Long widgetHistId, Boolean isLiked, Boolean isEvaluated, int widgetNo, String subject, String template, List<Values> values, Supplement supplement) {
            this.widgetHistId = widgetHistId;
            this.isLiked = isLiked;
            this.isEvaluated = isEvaluated;
            this.widgetNo = widgetNo;
            this.subject = subject;
            this.template = template;
            this.values = values;
            this.supplement = supplement;
        }

        @Getter
        static public class Values {
            private String text;
            private String style;

            @Builder
            public Values(String text, String style) {
                this.text = text;
                this.style = style;
            }
        }

        @Getter
        static public class Supplement {
            private List<String> type;
            private Box box;
            private RadarChart radarChart;
            private Table table;
            private List<Table> tables;
            private LineChart lineChart;
            private List<LineChart> lineCharts;

            @Builder
            public Supplement(List<String> type, Box box, RadarChart radarChart, Table table, List<Table> tables, LineChart lineChart, List<LineChart> lineCharts) {
                this.type = type;
                this.box = box;
                this.radarChart = radarChart;
                this.table = table;
                this.tables = tables;
                this.lineChart = lineChart;
                this.lineCharts = lineCharts;
            }

            @Getter
            static public class Box {
                private List<String> positive;
                private List<String> negative;
            }

            @Getter
            static public class RadarChart {
                private List<String> factors;
                private List<Series> series;

                @Builder
                public RadarChart(List<String> factors, List<Series> series) {
                    this.factors = factors;
                    this.series = series;
                }

                @Getter
                static public class Series {
                    private String category;
                    private String displayName;
                    private List<String> values;
                }

            }

            @Getter
            static public class Table {
                private String classfication;
                private String title;
                private String text;
                private List<String> headers;
                private List<String[]> rows;

                @Builder
                public Table(String classfication, String title, String text, List<String> headers, List<String[]> rows) {
                    this.classfication = classfication;
                    this.title = title;
                    this.text = text;
                    this.headers = headers;
                    this.rows = rows;
                }
            }

            @Getter
            @Builder
            static public class LineChart {
                private String title;
                private List<String> xaxis;
                private List<String> yaxis;
                private List<String> additionalValue;
            }

        }

    }

}
