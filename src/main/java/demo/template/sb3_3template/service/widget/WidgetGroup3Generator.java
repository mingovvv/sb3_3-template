package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.dto.widget.W11Dto;
import demo.template.sb3_3template.entity.mart.YhEcoClose;
import demo.template.sb3_3template.entity.mart.YhMarket;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRateM;
import demo.template.sb3_3template.entity.mart.yh.YhEcoMReturnRate;
import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;
import demo.template.sb3_3template.enums.Tag;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;
import demo.template.sb3_3template.repository.mart.YhMarketRepository;
import demo.template.sb3_3template.repository.mart.infostock.InfostockSectorIndexRepository;
import demo.template.sb3_3template.repository.mart.infostock.InfostockSectorReturnRateMRepository;
import demo.template.sb3_3template.repository.mart.yh.YhEcoCloseRepository;
import demo.template.sb3_3template.repository.mart.yh.YhEcoMReturnRateRepository;
import demo.template.sb3_3template.repository.mart.yh.YhStockMReturnRateRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WidgetGroup3Generator extends AbstractWidgetGenerator {

    private final YhMarketRepository yhMarketRepository;
    private final YhStockMReturnRateRepository yhStockMReturnRateRepository;
    private final InfostockSectorIndexRepository infostockSectorIndexRepository;
    private final InfostockSectorReturnRateMRepository infostockSectorReturnRateMRepository;
    private final YhEcoCloseRepository yhEcoCloseRepository;
    private final YhEcoMReturnRateRepository yhEcoMReturnRateRepository;

    public WidgetGroup3Generator(YhMarketRepository yhMarketRepository, YhStockMReturnRateRepository yhStockMReturnRateRepository, InfostockSectorIndexRepository infostockSectorIndexRepository, InfostockSectorReturnRateMRepository infostockSectorReturnRateMRepository, YhEcoCloseRepository yhEcoCloseRepository, YhEcoMReturnRateRepository yhEcoMReturnRateRepository) {
        this.yhMarketRepository = yhMarketRepository;
        this.yhStockMReturnRateRepository = yhStockMReturnRateRepository;
        this.infostockSectorIndexRepository = infostockSectorIndexRepository;
        this.infostockSectorReturnRateMRepository = infostockSectorReturnRateMRepository;
        this.yhEcoCloseRepository = yhEcoCloseRepository;
        this.yhEcoMReturnRateRepository = yhEcoMReturnRateRepository;
    }

    @Override
    public WidgetGroup getGroup() {
        return WidgetGroup.WIDGET_GROUP_3;
    }

    @Override
    protected WidgetResponse.Widget generateSpecificWidget(WidgetCreationDto dto, int widgetNo) {
        return switch (widgetNo) {
            case 11 -> generateWidget11(dto);
            default -> throw new IllegalStateException("Unexpected value: " + widgetNo);
        };
    }

    private WidgetResponse.Widget generateWidget11(WidgetCreationDto dto) {

        Map<String, List<WidgetCreationDto.Entity>> entityMap = dto.entityMap();

        Optional<WidgetCreationDto.Entity> sourceEntity = dto.entity().stream().filter(s -> s.stCode() == 1).findFirst();
        Optional<WidgetCreationDto.Entity> targetEntity = dto.entity().stream().filter(s -> s.stCode() == 2).findFirst();

        if (sourceEntity.isEmpty() || targetEntity.isEmpty()) return null;

        W11Dto sourceData = getWidget11Data(sourceEntity.get());
        if (ObjectUtils.isEmpty(sourceData)) return null;

        W11Dto targetData = getWidget11Data(targetEntity.get());
        if (ObjectUtils.isEmpty(targetData)) return null;

        return WidgetTemplateCreator.createWidget11Detail(sourceData, targetData);

    }

    private W11Dto getWidget11Data(WidgetCreationDto.Entity entity) {
        return switch (Tag.findByName(entity.tag())) {
            case STOCK -> generateWidgetStock(entity.entity());
            case INDEX -> generateWidgetEco(entity.entity());
            case SECTOR -> generateWidgetSector(entity.entity());
            default -> throw new IllegalStateException("Unexpected value: " + entity.tag());
        };
    }

    private W11Dto generateWidgetStock(String stockNm) {

        List<YhStockMReturnRate> stockMReturn = yhStockMReturnRateRepository.findByStockNmAndStockDtGoe(stockNm, "202210");
        if (stockMReturn.isEmpty()) return null;

        List<YhMarket> stockClose = yhMarketRepository.findByStockNmAndStdDtGoe(stockNm, "20221010");
        if (stockMReturn.isEmpty()) return null;

        return W11Dto.fromStock(stockMReturn, stockClose);
    }

    private W11Dto generateWidgetSector(String sectorNm) {

        List<InfostockSectorReturnRateM> sectorMReturn = infostockSectorReturnRateMRepository.findByThemeNmAndThemeDtGoe(sectorNm, "202210");
        if (sectorMReturn.isEmpty()) return null;

        List<InfostockSectorIndex> sectorClose = infostockSectorIndexRepository.findByThemeNmAndStdDtGoe(sectorNm, "20221010");
        if (sectorClose.isEmpty()) return null;

        return W11Dto.fromSector(sectorMReturn, sectorClose);
    }

    private W11Dto generateWidgetEco(String ecoNm) {

        List<YhEcoMReturnRate> ecoMReturn = yhEcoMReturnRateRepository.findEcoNmAndEcoDtGoe(ecoNm, "202210");
        List<YhEcoClose> ecoDayClose = yhEcoCloseRepository.findEcoNmAndStdDtGoe(ecoNm, "20221010", "D");
        List<YhEcoClose> ecoMonthClose = yhEcoCloseRepository.findEcoNmAndStdDtGoe(ecoNm, "20221010", "M");

        return W11Dto.fromEco(ecoMReturn, ecoDayClose, ecoMonthClose);
    }

}
