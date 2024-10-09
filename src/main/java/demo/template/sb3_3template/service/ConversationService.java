package demo.template.sb3_3template.service;

import demo.template.sb3_3template.dto.req.ConversationReq;
import demo.template.sb3_3template.dto.res.WatchlistRes;
import demo.template.sb3_3template.http.InferencePipelineApi;
import demo.template.sb3_3template.http.dto.InferencePipelineReq;
import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import demo.template.sb3_3template.model.WidgetResponse;
import demo.template.sb3_3template.service.widget.WidgetGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ConversationService {

    private final WidgetGenerator widgetGenerator;
    private final WidgetValidator widgetValidator;
    private final InferencePipelineApi inferencePipelineApi;

    public ConversationService(InferencePipelineApi inferencePipelineApi, WidgetValidator widgetValidator, WidgetGenerator widgetGenerator) {
        this.inferencePipelineApi = inferencePipelineApi;
        this.widgetValidator = widgetValidator;
        this.widgetGenerator = widgetGenerator;
    }

    @Transactional
    public WidgetResponse postConversation(ConversationReq conversationReq) {

        // 추론 파이프라인 호출
        InferencePipelineRes inferencePipelineRes = inferencePipelineApi.postInferenceExecution(InferencePipelineReq.of(conversationReq.question()));

        // 위젯그룹 검증
        List<Integer> validatedWidgetNo = widgetValidator.validate(inferencePipelineRes);

        // 위젯 생성
        WidgetResponse widgetResponse = widgetGenerator.generateWidget(inferencePipelineRes, validatedWidgetNo);

        return widgetResponse;

    }

}
