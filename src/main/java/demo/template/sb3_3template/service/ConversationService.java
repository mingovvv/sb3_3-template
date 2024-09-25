package demo.template.sb3_3template.service;

import demo.template.sb3_3template.dto.req.ConversationReq;
import demo.template.sb3_3template.dto.res.WatchlistRes;
import demo.template.sb3_3template.http.InferencePipelineApi;
import demo.template.sb3_3template.http.dto.InferencePipelineReq;
import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ConversationService {

    private final WidgetValidator widgetValidator;
    private final InferencePipelineApi inferencePipelineApi;

    public ConversationService(InferencePipelineApi inferencePipelineApi, WidgetValidator widgetValidator) {
        this.inferencePipelineApi = inferencePipelineApi;
        this.widgetValidator = widgetValidator;
    }

    @Transactional
    public WatchlistRes.PostWatch postConversation(ConversationReq conversationReq) {

        // todo 대화 저장
        // todo 대화 상세 저장
        // todo inferencePipelineApi 호출
        InferencePipelineRes inferencePipelineRes = inferencePipelineApi.postInferenceExecution(InferencePipelineReq.of(conversationReq.question()));

        List<Integer> validatedWidgetNo = widgetValidator.validate(inferencePipelineRes);

        return null;

    }

}
