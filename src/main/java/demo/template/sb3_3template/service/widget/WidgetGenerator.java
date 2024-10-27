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

        WidgetCreationDto.Entity source = WidgetCreationDto.Entity.builder().tag(Tag.STOCK.name()).entity("삼전").stCode(1).build();
        WidgetCreationDto.Entity target = WidgetCreationDto.Entity.builder().tag(Tag.FINANCE.name()).entity("수익률").stCode(2).build();

        List<WidgetCreationDto.Entity> list = List.of(source, target);

        WidgetCreationDto dto = new WidgetCreationDto(3, List.of(source, target), Map
                .of(), null, null, null);

        WidgetResponse res = strategies.get(WidgetGroup.WIDGET_GROUP_13).generate(dto, List.of(27), includeCommonWidget);

        // 공통 위젯 포함 여부 확인 후 동적으로 포함
//        if (includeCommonWidget && commonWidgetStrategy.shouldInclude(inferencePipelineRes)) {
//            response.addWidget(commonWidgetStrategy.generate());
//        }

        return res;
    }

}
