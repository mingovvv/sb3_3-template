package demo.template.sb3_3template.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record ConversationReq(

        @Schema(description = "유저_ID", example = "123456")
        String userId,

        @Schema(description = "발화타입", example = "TEXT", allowableValues = {"TEXT", "VOICE", "BUTTON", "RECOMMEND"})
        @NotEmpty
        String utterType,

        @Schema(description = "질의내용", example = "삼성전자 전망 어때요?")
        String question,

        @Schema(description = "버튼타입", example = "미정", allowableValues = {"미정"})
        String buttonType

) {

}
