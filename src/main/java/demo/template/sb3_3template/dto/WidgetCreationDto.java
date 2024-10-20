package demo.template.sb3_3template.dto;

import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Builder
public record WidgetCreationDto(

        int widgetGroup,

        List<Entity> entity,

        Widget5 widget5,

        List<Widget6> widget6,

        List<News> news

) {

    @Builder
    public record Entity(
            String entity,
            String tag,
            int stCode
    ) {}

    @Builder
    public record Widget5(
            String event,
            String date
    ) {}

    @Builder
    public record Widget6(
            String event,
            String date
    ) {}

    @Builder
    public record News(
            String summary,
            List<NewsObject> newsObject
    ) {

        @Builder
        public record NewsObject(
                String newsId,
                String title,
                String url
        ) {

        }
    }

    public static WidgetCreationDto from(InferencePipelineRes inferencePipelineRes) {

        List<Entity> entities = Optional.ofNullable(inferencePipelineRes.questionMeta())
                .map(qm -> qm.ner())
                .map(ner -> ner.entities())
                .map(e -> e.stream().map(s -> new Entity(s.entity(), s.tag(), s.stCode())).toList())
                .orElse(Collections.emptyList());

        return WidgetCreationDto.builder()
                .widgetGroup(inferencePipelineRes.widgetGroup())
                .entity(entities)
                .build();
    }

}
