package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.dto.projection.StockInSectorDto;
import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRate;
import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.enums.BsnsDays;
import demo.template.sb3_3template.enums.RankType;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;
import demo.template.sb3_3template.repository.mart.infostock.InfostockSectorReturnRateRepository;
import demo.template.sb3_3template.repository.mart.YhEcoReturnRateRepository;
import demo.template.sb3_3template.repository.mart.YhMarketRepository;
import demo.template.sb3_3template.repository.mart.YhStockReturnRateRepository;
import demo.template.sb3_3template.repository.mart.infostock.InfostockThemeStockMasterRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class WidgetGroup15Generator extends AbstractWidgetGenerator {

    private final YhMarketRepository yhMarketRepository;
    private final YhEcoReturnRateRepository yhEcoReturnRateRepository;
    private final YhStockReturnRateRepository yhStockReturnRateRepository;
    private final InfostockSectorReturnRateRepository infostockSectorReturnRateRepository;
    private final InfostockThemeStockMasterRepository infostockThemeStockMasterRepository;

    protected WidgetGroup15Generator(YhMarketRepository yhMarketRepository, YhEcoReturnRateRepository yhEcoReturnRateRepository, YhStockReturnRateRepository yhStockReturnRateRepository, InfostockSectorReturnRateRepository infostockSectorReturnRateRepository, InfostockThemeStockMasterRepository infostockThemeStockMasterRepository) {
        this.yhMarketRepository = yhMarketRepository;
        this.yhEcoReturnRateRepository = yhEcoReturnRateRepository;
        this.yhStockReturnRateRepository = yhStockReturnRateRepository;
        this.infostockSectorReturnRateRepository = infostockSectorReturnRateRepository;
        this.infostockThemeStockMasterRepository = infostockThemeStockMasterRepository;
    }

    @Override
    public WidgetGroup getGroup() {
        return WidgetGroup.WIDGET_GROUP_15;
    }

    @Override
    protected WidgetResponse.Widget generateSpecificWidget(WidgetCreationDto dto, int widgetNo) {
        return switch (widgetNo) {
            case 29 -> generateWidget29(dto);
            default -> throw new IllegalStateException("Unexpected value: " + widgetNo);
        };
    }

    private WidgetResponse.Widget generateWidget29(WidgetCreationDto dto) {

        Map<RankType, List<StockReturnMkCap>> stockReturnMap = yhStockReturnRateRepository.findByStdDtAndBsnsDays("20241026", BsnsDays.ONE_MONTH);

        Map<RankType, List<YhEcoReturnRate>> ecoReturnRates = yhEcoReturnRateRepository.findLastestIndicatorsByEcoTypeAndBsnsDays("주식/지수", BsnsDays.ONE_MONTH);

        Map<RankType, List<InfostockSectorReturnRate>> sectorReturnMap = infostockSectorReturnRateRepository.findByStdDtAndBsnsDays("20241026", BsnsDays.ONE_MONTH);

        List<String> sectorCdList = sectorReturnMap.values().stream().flatMap(Collection::stream).map(InfostockSectorReturnRate::getThemeCd).toList();

        List<StockInSectorDto> stockInSector = infostockThemeStockMasterRepository.findBySectorCdAndStdDt(sectorCdList, "20241026");

        return WidgetTemplateCreator.createWidget29Detail(stockReturnMap, ecoReturnRates, sectorReturnMap, stockInSector);
    }


}
