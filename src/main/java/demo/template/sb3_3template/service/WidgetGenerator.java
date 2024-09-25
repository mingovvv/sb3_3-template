package demo.template.sb3_3template.service;

import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import demo.template.sb3_3template.enums.Tag;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import demo.template.sb3_3template.repository.mart.YhEcoReturnRateRepository;
import demo.template.sb3_3template.repository.mart.YhStockCodeRepository;
import demo.template.sb3_3template.repository.mart.YhStockReturnRateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WidgetGenerator {

    private final YhEcoReturnRateRepository yhEcoReturnRateRepository;
    private final YhStockCodeRepository yhStockCodeRepository;
    private final YhStockReturnRateRepository yhStockReturnRateRepository;

    public WidgetGenerator(YhEcoReturnRateRepository yhEcoReturnRateRepository, YhStockCodeRepository yhStockCodeRepository, YhStockReturnRateRepository yhStockReturnRateRepository) {
        this.yhEcoReturnRateRepository = yhEcoReturnRateRepository;
        this.yhStockCodeRepository = yhStockCodeRepository;
        this.yhStockReturnRateRepository = yhStockReturnRateRepository;
    }

    public void generateWidget(InferencePipelineRes inferencePipelineRes, String Object) {

        switch (WidgetGroup.findWidgetGroup(inferencePipelineRes.widgetGroup())) {

            case WIDGET_GROUP_1 -> {

                Optional<String> tagName = inferencePipelineRes.questionMeta().ner().entities().stream().map(InferencePipelineRes.QuestionMeta.Ner.Entities::tag).findFirst();

                WidgetGroup.Widget[] widgets = WidgetGroup.WIDGET_GROUP_1.getWidgets();
                WidgetGroup.Widget widget5_1 = widgets[0];
                WidgetGroup.Widget widget5_2 = widgets[1];

                tagName.ifPresent(tag -> {

                    Tag tag1 = Tag.findByName(tagName.get());
                    Optional<String> entityName = inferencePipelineRes.questionMeta().ner().entities().stream().map(InferencePipelineRes.QuestionMeta.Ner.Entities::entity).findFirst();

                    if (tag1 == Tag.STOCK) {

                        // todo 종목의 타겟일+1D 변동률 구하기
//                        YhStockCodeRepository stockReturnRate = yhStockCodeRepository.findStockReturnRate(entityName.get(), "1", "20240505").orElse(null);

                        // todo 지수의 타겟일+1D 변동률 구하기
//                        YhEcoReturnRate indexReturnRate = yhEcoReturnRateRepository.findByEcoCodeAndBsnsDaysAndStdDt("KOSPI", "1", "20240505").orElse(null);


                    } else if (tag1 == Tag.INDEX) {

                        // todo 지수의 타겟일+1D 변동률 구하기


                    } else {

                        // todo 섹터의 타겟일+1D 변동률 구하기

                    }

                });

//                Arrays.stream(widgets).map(s -> {
//                    int widgetNo = s.getWNo();
//                    String[] templates = s.getTemplate();
//                    if (Object.equals("종목")) {
//                        String template = templates[0];
//                    }
//                })



            }

            default -> {

            }

        }

    }

}
