package demo.template.sb3_3template.service.widget;

import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.entity.fs.Fs;
import demo.template.sb3_3template.enums.FinancialClassifier;
import demo.template.sb3_3template.enums.Tag;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;
import demo.template.sb3_3template.repository.fs.FsRepository;
import demo.template.sb3_3template.repository.mart.YhEcoReturnRateRepository;
import demo.template.sb3_3template.repository.mart.YhMarketRepository;
import demo.template.sb3_3template.repository.mart.YhStockReturnRateRepository;
import demo.template.sb3_3template.repository.mart.infostock.InfostockSectorReturnRateRepository;
import demo.template.sb3_3template.repository.mart.infostock.InfostockThemeStockMasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WidgetGroup13Generator extends AbstractWidgetGenerator {

    private final FsRepository fsRepository;

    public WidgetGroup13Generator(FsRepository fsRepository) {
        this.fsRepository = fsRepository;
    }

    @Override
    public WidgetGroup getGroup() {
        return WidgetGroup.WIDGET_GROUP_13;
    }

    @Override
    protected WidgetResponse.Widget generateSpecificWidget(WidgetCreationDto dto, int widgetNo) {
        return switch (widgetNo) {
            case 27 -> generateWidget27(dto);
            default -> throw new IllegalStateException("Unexpected value: " + widgetNo);
        };
    }

    private WidgetResponse.Widget generateWidget27(WidgetCreationDto dto) {

        List<String> optional = List.of(Tag.NORMAL.getTagName(), Tag.COMPRISON.getTagName());

        Optional<WidgetCreationDto.Entity> period = dto.entity().stream().filter(d -> optional.contains(d.tag())).findFirst();

        WidgetCreationDto.Entity stockEntity = dto.entityMap().get(Tag.STOCK.getTagName()).get(0);
        WidgetCreationDto.Entity financeEntity = dto.entityMap().get(Tag.FINANCE.getTagName()).get(0);

        FinancialClassifier financialCode = FinancialClassifier.findByFsNm(financeEntity.entity());

        List<Fs> fs;

        if (period.isPresent()) {
            // 기간의 마지막 연간 재무제표
            WidgetCreationDto.DateFrame dateFrame = dto.dateFrame();

            String endYearOfPeriod = DateUtil.getLatestFinancialStatementYear(dateFrame.end());

            // 최근 6년치 데이터 조회
            fs = fsRepository.findFs(DateUtil.getMinusYear(5), endYearOfPeriod);

            return WidgetTemplateCreator.createWidget27Detail(stockEntity, financeEntity, endYearOfPeriod, fs);
        } else {

            // 최근 6년치 데이터 조회
            fs = fsRepository.findFs(DateUtil.getMinusYear(5), null);

            return WidgetTemplateCreator.createWidget27Detail(stockEntity, financeEntity, null, fs);
        }

    }

}
