package demo.template.sb3_3template.service;

import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WidgetValidator {

    public List<Integer> validate(InferencePipelineRes inferencePipelineRes) {
        return List.of(1);
    }

}
