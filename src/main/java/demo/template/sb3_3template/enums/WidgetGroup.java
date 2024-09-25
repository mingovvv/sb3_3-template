package demo.template.sb3_3template.enums;

import lombok.Getter;

import static demo.template.sb3_3template.enums.WidgetGroup.Widget.*;

@Getter
public enum WidgetGroup {

    WIDGET_GROUP_1(1, WIDGET_5, WIDGET_6, WIDGET_7, WIDGET_8);

    private int wgNo;
    private Widget[] widgets;

    WidgetGroup(int wgNo, Widget... widget) {
        this.wgNo = wgNo;
        this.widgets = widget;
    }

    enum Widget {

        WIDGET_5(5, """
                최근 {} 관련 주요 뉴스는 {} 이에요.
                비슷한 뉴스가 있었을 때, {} 주가는 평균적으로 다음날까지 {} 올랐고, {}보다 {} 성과를 보였어요.
                """, """
                최근 {} 관련 주요 뉴스는 {} 이에요.
                비슷한 뉴스가 있었을 때, {} 주가는 평균적으로 다음날까지 {} 올랐어요.
                """),
        WIDGET_6(6, """
                지금까지 뉴스를 분석해보면 {}의 주가는 아래 이벤트가 중요해요.
                {#1}
                {} 주가는 {}에 가장 많이 올랐고, {}에 가장 많이 올랐고, {}에 가장 많이 내렸어요.
                """),
        WIDGET_7(7, """
                최근 1개월간 {}의 수익률을 분석해본 결과 시장에서 {} 수익률을 보이는 주요 스타일과 {}해요. {}주가 변화는 국내 시장 분위기에 영향을 많이 {}.
                
                {}의 {} {}, {} {} 스타일이최근 1개월 수익에 긍정적인 영향을 미치고 있는 중이에요.
                반면, {} {}, {} {} 스타일은 부정적으로 작용하고 있어요.
                """),
        WIDGET_8(8, """
                """);

        private int wNo;
        private String[] template;


        Widget(int wNo, String... template) {
            this.wNo = wNo;
            this.template = template;
        }

    }

}