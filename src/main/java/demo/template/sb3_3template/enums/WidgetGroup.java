package demo.template.sb3_3template.enums;

import lombok.Getter;

import java.util.Arrays;

import static demo.template.sb3_3template.enums.WidgetGroup.Widget.*;

@Getter
public enum WidgetGroup {

    WIDGET_GROUP_1(1, true, WIDGET_5, WIDGET_6, WIDGET_7, WIDGET_8),
    WIDGET_GROUP_2(2, true, WIDGET_5, WIDGET_6, WIDGET_7, WIDGET_8),
    WIDGET_GROUP_3(3, true, WIDGET_11),
    WIDGET_GROUP_6(6, true, WIDGET_18),
    WIDGET_GROUP_13(13, true, WIDGET_29),
    WIDGET_GROUP_15(15, true, WIDGET_29);

    private int wedgetGroupNo;
    private boolean isUsedCommonWidget;
    private Widget[] widgets;

    WidgetGroup(int wedgetGroupNo, boolean isUsedCommonWidget, Widget... widgets) {
        this.wedgetGroupNo = wedgetGroupNo;
        this.isUsedCommonWidget = isUsedCommonWidget;
        this.widgets = widgets;
    }

    public static WidgetGroup findWidgetGroup(int wedgetGroupNo) {
        return Arrays.stream(WidgetGroup.values())
               .filter(wg -> wg.getWedgetGroupNo() == wedgetGroupNo)
               .findFirst()
               .orElseThrow(() -> new IllegalArgumentException("WidgetGroup not found."));
    }

    @Getter
    public enum Widget {

        WIDGET_5(5,
                """
                최근 {#1} 관련 주요 뉴스는 {#2} {#3} 이에요.
                비슷한 뉴스가 있었을 때, {#4} 주가는 평균적으로 다음날까지 {#5}% 올랐고, {#6}보다 {#7}% 성과를 보였어요.
                """,
                """
                최근 {#1} 관련 주요 뉴스는 {#2} {#3} 이에요.
                비슷한 뉴스가 있었을 때, {#4} 주가는 평균적으로 다음날까지 {#5}% 올랐어요.
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
                """),
        WIDGET_11(11, """
                {}와 {}의 움직임을 최근 2년간 분석했다. 상승일치 비율은 {}%이다. {}가 {}번 올랐을 때, {}는 {}번 올랐다. 하락일치 비율은 {}%이다. {}가 {}번 내렸을때, {}은 {}번 내렸다. 
                """),
        WIDGET_18(18, """
                {}이 {}때 아래의 종목들이 같이 {} 하는 경향이 있었어요.
                |
                {}이 {}때 아래의 종목들이 같이 {} 하는 경향이 있었어요.
                """),
        WIDGET_27(27, """
                {} {} 의 {} 는 {} 입니다.
                최근 5년간 추세와 함께 살펴보세요.
                """),
        WIDGET_29(29, "");

        private int wNo;
        private String[] template;


        Widget(int wNo, String... template) {
            this.wNo = wNo;
            this.template = template;
        }

    }

}