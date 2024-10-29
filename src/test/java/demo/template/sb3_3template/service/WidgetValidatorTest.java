package demo.template.sb3_3template.service;

import demo.template.common.enums.ResultCode;
import demo.template.common.exception.AppErrorException;
import demo.template.sb3_3template.enums.MarketType;
import demo.template.sb3_3template.enums.Tag;
import demo.template.sb3_3template.http.dto.InferencePipelineRes;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class WidgetValidatorTest {

    @Test
    void widgetValidatorTest() {

        InferencePipelineRes res = new InferencePipelineRes(5, 0.0, new InferencePipelineRes.QuestionMeta(
                new InferencePipelineRes.QuestionMeta.Ner("",
                        List.of(InferencePipelineRes.QuestionMeta.Ner.Entities.builder()
                                .tag("OG_COMPANY")
                                .build(),
                                InferencePipelineRes.QuestionMeta.Ner.Entities.builder()
                                .tag("기준금리")
                                .build()
                        )),
                null,
                null
        ), null);

        List<InferencePipelineRes.QuestionMeta.Ner.Entities> entities = res.questionMeta().ner().entities();
        List<String> tagList = entities.stream().map(InferencePipelineRes.QuestionMeta.Ner.Entities::tag).toList();


        List<Integer> result = switch (res.widgetGroup()) {
            case 1 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>(List.of(5, 6));
                if (Collections.frequency(tagList, Tag.STOCK.getTagName()) == 1) defaultWidgetNo.addAll(List.of(7, 8));
                yield defaultWidgetNo;
            }
            case 2 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>();
                if (Collections.frequency(tagList, Tag.STOCK.getTagName()) == 1 && Collections.frequency(tagList, Tag.재무.getTagName()) == 1) defaultWidgetNo.add(9);
                yield defaultWidgetNo;
            }
            case 3 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>();
                Set<String> listup = Set.of(Tag.STOCK.getTagName(), Tag.SECTOR.getTagName(), Tag.INDEX.getTagName(), Tag.원자재.getTagName(), Tag.채권.getTagName(), Tag.외환.getTagName(), Tag.경제.getTagName(), Tag.기준금리.getTagName());
                if (tagList.stream().filter(listup::contains).count() == 2)
                if (listup.containsAll(tagList)) defaultWidgetNo.add(10);
                yield defaultWidgetNo;
            }
            case 4 -> List.of(12);
            case 5 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>(List.of(14, 15, 16));
                if (tagList.contains(Tag.SECTOR.getTagName())) defaultWidgetNo.add(13);
                if (tagList.contains(Tag.배당.getTagName())) defaultWidgetNo.add(17);
                yield defaultWidgetNo;
            }
            case 6 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>();
                Set<String> listup = Set.of(Tag.STOCK.getTagName(), Tag.SECTOR.getTagName(), Tag.INDEX.getTagName(), Tag.원자재.getTagName(), Tag.채권.getTagName(), Tag.외환.getTagName(), Tag.경제.getTagName());
                if (listup.stream().filter(tagList::contains).count() == 1) defaultWidgetNo.add(18);
                yield defaultWidgetNo;
            }
            case 7 -> List.of(19);
            case 8 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>();
                Set<String> listup = Set.of(Tag.STOCK.getTagName(), Tag.SECTOR.getTagName(), Tag.INDEX.getTagName(), Tag.원자재.getTagName(), Tag.채권.getTagName(), Tag.외환.getTagName(), Tag.경제.getTagName(), Tag.기준금리.getTagName());
                if (listup.stream().filter(tagList::contains).count() == 1) defaultWidgetNo.add(20);
                yield defaultWidgetNo;
            }
            case 9 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>();
                if (Collections.frequency(tagList, Tag.STOCK.getTagName()) == 1) defaultWidgetNo.add(21);
                yield defaultWidgetNo;
            }
            case 10 -> List.of(22);
            case 11 -> List.of(23, 24);
            case 12 -> List.of(25, 26);
            case 13 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>();
                if (Collections.frequency(tagList, Tag.STOCK.getTagName()) == 1 && Collections.frequency(tagList, Tag.재무.getTagName()) == 1) defaultWidgetNo.add(27);
                yield defaultWidgetNo;
            }
            case 14 -> {
                ArrayList<Integer> defaultWidgetNo = new ArrayList<>();
                if (Collections.frequency(tagList, Tag.팩터명.getTagName()) == 1) defaultWidgetNo.add(28);
                yield defaultWidgetNo;
            }
            case 15 -> List.of(29);
            default -> List.of(-1);
        };

        System.out.println(result);


    }

}