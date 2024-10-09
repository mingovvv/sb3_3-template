package demo.template.sb3_3template.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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

    static public class Widget {
        private int widgetNo;
        private String template;
        private List<Values> values;
        private Supplement supplement;

        @Builder
        public Widget(int widgetNo, String template, List<Values> values, Supplement supplement) {
            this.widgetNo = widgetNo;
            this.template = template;
            this.values = values;
            this.supplement = supplement;
        }

        static public class Values {
            private String text;
            private String style;

            public Values(String text, String style) {
                this.text = text;
                this.style = style;
            }
        }

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

            static public class Box {
                private List<String> positive;
                private List<String> negative;
            }

            static public class RadarChart {
                private List<String> factors;
                private List<Series> series;

                @Builder
                public RadarChart(List<String> factors, List<Series> series) {
                    this.factors = factors;
                    this.series = series;
                }

                static public class Series {
                    private String category;
                    private String displayName;
                    private List<String> values;
                }

            }

            static public class Table {
                private List<String> headers;
                private List<String[]> rows;

                public Table(List<String> headers, List<String[]> rows) {
                    this.headers = headers;
                    this.rows = rows;
                }
            }

            static public class LineChart {
                // todo
            }

        }1

    }

}
