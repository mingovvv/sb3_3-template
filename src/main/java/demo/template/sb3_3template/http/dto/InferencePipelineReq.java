package demo.template.sb3_3template.http.dto;

import lombok.Builder;

public record InferencePipelineReq(

        String question

) {

    @Builder
    public InferencePipelineReq(String question) {
        this.question = question;
    }

    public static InferencePipelineReq of(String question) {
        return InferencePipelineReq.builder()
                .question(question)
                .build();
    }

}
