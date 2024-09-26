package demo.template.sb3_3template.service;

import demo.template.sb3_3template.dto.StockRateOfReturnDto;
import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import demo.template.sb3_3template.enums.IndexKrType;
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

    /**
     *
     * @param inferencePipelineRes 추론 파이프라인 응답 객체
     * @param Object
     */
    public void generateWidget(InferencePipelineRes inferencePipelineRes, String Object) {

        switch (WidgetGroup.findWidgetGroup(inferencePipelineRes.widgetGroup())) {

            case WIDGET_GROUP_1 -> {

                // 태그 추출하기
                Optional<String> tagName = inferencePipelineRes.questionMeta().ner().entities().stream().map(InferencePipelineRes.QuestionMeta.Ner.Entities::tag).findFirst();

                // 위젯 그룹 1번 데이터 가져오기
                WidgetGroup.Widget[] widgets = WidgetGroup.WIDGET_GROUP_1.getWidgets();


                tagName.ifPresent(tag -> {

                    Optional<String> entityName = inferencePipelineRes.questionMeta().ner().entities().stream().map(InferencePipelineRes.QuestionMeta.Ner.Entities::entity).findFirst();

                    if (Tag.findByName(tagName.get()) == Tag.STOCK) {

                        // 템플릿 가져오기
                        WidgetGroup.Widget widget5 = widgets[0];

                        // 종목의 타겟일 기준 D+1일 변동률 조회
                        StockRateOfReturnDto stockRateOfReturnDto = yhStockCodeRepository.findStockRateOfReturn(entityName.get(), 1, "20240505").orElse(StockRateOfReturnDto.ofEmpty());

                        // 종목이 속한 지수의 기준 D+1일 변동률 조회
                        YhEcoReturnRate yhEcoReturnRate = yhEcoReturnRateRepository.findByEcoCodeAndBsnsDaysAndStdDt(IndexKrType.findByExcngId(stockRateOfReturnDto.excngId()).getEcoCode(), 1, "20240505").orElse(YhEcoReturnRate.ofEmpty());

                        String temp = widget5.getTemplate()[0];
                        String entity = entityName.get();
                        String date = "";
                        String event = "";
                        String stockReturn = String.valueOf(stockRateOfReturnDto.returnRate());
                        String excngName = IndexKrType.findByExcngId(stockRateOfReturnDto.excngId()).getExcngName();
                        String excessReturn= String.valueOf(yhEcoReturnRate.getReturnRate() - stockRateOfReturnDto.returnRate());

                        WidgetObjCreator.createWidgetObject(widget5, entityName.get(), date, event, stockReturn, excngName, excessReturn);


                    } else if (Tag.findByName(tagName.get()) == Tag.INDEX) {

                        // 템플릿 가져오기
                        WidgetGroup.Widget widget5_2 = widgets[1];

                        // todo 지수의 타겟일+1D 변동률 구하기


                    } else {

                        // 템플릿 가져오기
                        WidgetGroup.Widget widget5_2 = widgets[1];

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
