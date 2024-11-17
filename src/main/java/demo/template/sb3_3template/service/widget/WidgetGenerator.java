package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.enums.Tag;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import demo.template.sb3_3template.model.WidgetResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service(value = "widgetGenerator2")
public class WidgetGenerator {

    private final Map<WidgetGroup, WidgetGeneratorStrategy> strategies;

    public WidgetGenerator(List<WidgetGeneratorStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(WidgetGeneratorStrategy::getGroup, Function.identity()));
    }

    /**
     *
     * @param inferencePipelineRes 추론 파이프라인 응답 객체
     * @param allowedWidgetNoList 검증된 위젯
     * @param includeCommonWidget 공통 위젯을 포함할지 여부
     */
    public WidgetResponse generateWidget(InferencePipelineRes inferencePipelineRes, List<Integer> allowedWidgetNoList, boolean includeCommonWidget) {

//        WidgetGroup group = WidgetGroup.findWidgetGroup(inferencePipelineRes.widgetGroup());

        WidgetCreationDto.Entity e1 = WidgetCreationDto.Entity.builder().tag(Tag.STOCK.name()).entity("삼성전자").stCode(1).build();
        WidgetCreationDto.Entity e2 = WidgetCreationDto.Entity.builder().tag(Tag.STOCK.name()).entity("셀트리온").stCode(2).build();
        WidgetCreationDto.Entity e3 = WidgetCreationDto.Entity.builder().tag(Tag.NORMAL.name()).entity("ㅋㅋ").tag("MK_NORMAL").stCode(2).build();


        WidgetCreationDto dto = new WidgetCreationDto(3, List.of(e1, e2, e3), Map
                .of(
                        Tag.STOCK.getTagName(), List.of(new WidgetCreationDto.Entity("삼성전자", "", 1)),
                        Tag.FINANCE.getTagName(), List.of(new WidgetCreationDto.Entity("자산", "", 1)),
                        Tag.NORMAL.getTagName(), List.of(new WidgetCreationDto.Entity("자산", "", 1))
                ), null, null, null, new WidgetCreationDto.DateFrame("2020-10-10 10:10:10", "2025-10-10 10:10:10"));
//
        WidgetResponse res = strategies.get(WidgetGroup.WIDGET_GROUP_3).generate(dto, List.of(11), includeCommonWidget);

        // 공통 위젯 포함 여부 확인 후 동적으로 포함
//        if (includeCommonWidget && commonWidgetStrategy.shouldInclude(inferencePipelineRes)) {
//            response.addWidget(commonWidgetStrategy.generate());
//        }

        return res;
    }

}
