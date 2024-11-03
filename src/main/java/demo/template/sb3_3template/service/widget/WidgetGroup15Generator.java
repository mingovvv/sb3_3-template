package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.dto.projection.IndexReturnCountry;
import demo.template.sb3_3template.dto.projection.SectorReturn;
import demo.template.sb3_3template.dto.projection.StockInSectorDto;
import demo.template.sb3_3template.dto.projection.StockReturnMkCap;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRate;
import demo.template.sb3_3template.entity.mart.YhEcoReturnRate;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.enums.BsnsDays;
import demo.template.sb3_3template.enums.RankType;
import demo.template.sb3_3template.enums.Tag;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;
import demo.template.sb3_3template.repository.mart.infostock.InfostockSectorIndexRepository;
import demo.template.sb3_3template.repository.mart.infostock.InfostockSectorReturnRateRepository;
import demo.template.sb3_3template.repository.mart.YhEcoReturnRateRepository;
import demo.template.sb3_3template.repository.mart.YhMarketRepository;
import demo.template.sb3_3template.repository.mart.YhStockReturnRateRepository;
import demo.template.sb3_3template.repository.mart.infostock.InfostockThemeStockMasterRepository;
import demo.template.sb3_3template.repository.mart.yh.YhEcoCloseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class WidgetGroup15Generator extends AbstractWidgetGenerator {

    private final YhMarketRepository yhMarketRepository;
    private final YhEcoCloseRepository yhEcoCloseRepository;
    private final YhEcoReturnRateRepository yhEcoReturnRateRepository;
    private final YhStockReturnRateRepository yhStockReturnRateRepository;
    private final InfostockSectorIndexRepository infostockSectorIndexRepository;
    private final InfostockSectorReturnRateRepository infostockSectorReturnRateRepository;
    private final InfostockThemeStockMasterRepository infostockThemeStockMasterRepository;

    protected WidgetGroup15Generator(YhMarketRepository yhMarketRepository, YhEcoCloseRepository yhEcoCloseRepository, YhEcoReturnRateRepository yhEcoReturnRateRepository, YhStockReturnRateRepository yhStockReturnRateRepository, InfostockSectorReturnRateRepository infostockSectorReturnRateRepository, InfostockSectorIndexRepository infostockSectorIndexRepository, InfostockThemeStockMasterRepository infostockThemeStockMasterRepository) {
        this.yhMarketRepository = yhMarketRepository;
        this.yhEcoCloseRepository = yhEcoCloseRepository;
        this.yhEcoReturnRateRepository = yhEcoReturnRateRepository;
        this.yhStockReturnRateRepository = yhStockReturnRateRepository;
        this.infostockSectorReturnRateRepository = infostockSectorReturnRateRepository;
        this.infostockSectorIndexRepository = infostockSectorIndexRepository;
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

        List<String> optional = List.of(Tag.NORMAL.getTagName(), Tag.COMPRISON.getTagName());

        Optional<WidgetCreationDto.Entity> period = dto.entity().stream().filter(d -> optional.contains(d.tag())).findFirst();

        Map<RankType, List<StockReturnMkCap>> stockReturnMap;
        Map<RankType, List<IndexReturnCountry>> ecoReturnMap;
        Map<RankType, List<SectorReturn>> sectorReturnMap;

        if (period.isPresent()) {
            log.info("기간 O");
            WidgetCreationDto.DateFrame dateFrame = dto.dateFrame();
            stockReturnMap = yhMarketRepository.findStockBy(dateFrame.start(), dateFrame.end());
            ecoReturnMap = yhEcoCloseRepository.findEcoBy(dateFrame.start(), dateFrame.end());
            sectorReturnMap = infostockSectorIndexRepository.findSectorBy(dateFrame.start(), dateFrame.end());
        } else {
            log.info("기간 X");
            stockReturnMap = yhStockReturnRateRepository.findByStdDtAndBsnsDays("20241026", BsnsDays.ONE_MONTH);
            ecoReturnMap = yhEcoReturnRateRepository.findLastestIndicatorsByEcoTypeAndBsnsDays("주식/지수", BsnsDays.ONE_MONTH);
            sectorReturnMap = infostockSectorReturnRateRepository.findByStdDtAndBsnsDays("20241026", BsnsDays.ONE_MONTH);

        }

        List<String> sectorCdList = sectorReturnMap.values().stream().flatMap(Collection::stream).map(SectorReturn::sectorCd).toList();

        List<StockInSectorDto> stockInSector = infostockThemeStockMasterRepository.findBySectorCdAndStdDt(sectorCdList, "20241026");

        return WidgetTemplateCreator.createWidget29Detail(stockReturnMap, ecoReturnMap, sectorReturnMap, stockInSector);
    }


}
