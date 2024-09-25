package demo.template.sb3_3template.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public record InferencePipelineRes(

        @JsonProperty("widget_group")
        int widgetGroup,

        double score,

        @JsonProperty("question_meta")
        QuestionMeta questionMeta,

        @JsonProperty("widget_group_based_results")
        String widgetGroupBasedResults

) {

    public record QuestionMeta(

            Ner ner,

            Eex eex,

            Nsm nsm

    ) {

        public record Ner(

            @JsonProperty("tagged_sentence")
            String taggedSentence,

            List<Entities> entities

        ) {

            public record Entities(

                    String entity,
                    String tag,
                    @JsonProperty("st_code")
                    int stCode
            ) {

            }

        }

        public record Eex(

            String event,

            List<String> keywords

        ) {

        }

        public record Nsm(

            String sector,

            double sectorScore,

            @JsonProperty("sentiment_score")
            double sentimentScore

        ) {

        }

    }

}
