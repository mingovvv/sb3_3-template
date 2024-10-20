package demo.template.sb3_3template.http.dto;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class InferencePipelineResTest {

    @Test
    void test() {

        InferencePipelineRes res = new InferencePipelineRes(1, 0.0, new InferencePipelineRes.QuestionMeta(null, null, null), null);

//        String event1 = OptionalUtil.safelyAccess(() -> res.questionMeta().eex().event()).orElse(null);

        String event2 = Optional.ofNullable(res.questionMeta())
                .map(InferencePipelineRes.QuestionMeta::eex)
                .map(InferencePipelineRes.QuestionMeta.Eex::event)
                .orElse(null);

        System.out.println();

        List<String> sentences = List.of("Hello World", "Java Stream API");
//        List<String> words = sentences.stream()
//                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))  // 각 문장을 단어로 나눈 스트림을 펼침
//                .collect(Collectors.toList());

        List<String[]> list = sentences.stream()
                .map(s -> s.split(" "))

                .toList();

    }

    @Test
    void test2() {
        WidgetCreationDto widgetCreationDto = null;



    }


}