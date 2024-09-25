package demo.template.sb3_3template.controller;

import demo.template.common.model.BaseResponse;
import demo.template.common.model.BaseResponseFactory;
import demo.template.sb3_3template.dto.req.ConversationReq;
import demo.template.sb3_3template.dto.res.WatchlistRes;
import demo.template.sb3_3template.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "05. 질의하기", description = "질의 관리 API")
@RestController
@RequestMapping("/v1/qna")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @Operation(summary = "질의하기 API", description = "질의합니다.")
    @ApiResponse(responseCode = "200", description = "HTTP Status Code is 200")
    @PostMapping("/conversation")
    public BaseResponse<WatchlistRes.PostWatch> postConversation(
            @RequestBody @Valid ConversationReq conversationReq
    ) {
        return BaseResponseFactory.create(conversationService.postConversation(conversationReq));
    }

}
