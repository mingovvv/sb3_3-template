package demo.template.sb3_3template.http;

import demo.template.sb3_3template.http.dto.InferencePipelineReq;
import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface InferencePipelineApi {

    @PostExchange("/api/v1/inference")
    InferencePipelineRes postInferenceExecution(@RequestBody InferencePipelineReq inferencePipelineReq);

}
