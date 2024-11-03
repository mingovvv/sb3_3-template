package demo.template.sb3_3template.dto;

import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
public record WidgetCreationDto(

        int widgetGroup,

        List<Entity> entity,

        Map<String, List<Entity>> entityMap,

        Widget5 widget5,

        List<Widget6> widget6,

        List<News> news,

        DateFrame dateFrame

) {

    @Builder
    public WidgetCreationDto(int widgetGroup, List<Entity> entity, Map<String, List<Entity>> entityMap, Widget5 widget5, List<Widget6> widget6, List<News> news, DateFrame dateFrame) {
        this.widgetGroup = widgetGroup;
        this.entity = entity;
        this.entityMap = entityMap;
        this.widget5 = widget5;
        this.widget6 = widget6;
        this.news = news;
        this.dateFrame = dateFrame;
    }

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

    @Builder
    public record DateFrame(
            String start,
            String end
    ) {

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
