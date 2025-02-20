package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.dto.projection.StockMonthlyReturn;
import demo.template.sb3_3template.dto.widget.ItemRateValDto;
import demo.template.sb3_3template.dto.widget.W11Dto;
import demo.template.sb3_3template.dto.widget.W18Dto;
import demo.template.sb3_3template.entity.mart.YhEcoClose;
import demo.template.sb3_3template.entity.mart.YhMarket;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRateM;
import demo.template.sb3_3template.entity.mart.yh.YhEcoMReturnRate;
import demo.template.sb3_3template.entity.mart.yh.YhStockMReturnRate;
import demo.template.sb3_3template.enums.StockCategory;
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
import java.util.stream.Stream;

@Service
public class WidgetGroup6Generator extends AbstractWidgetGenerator {

    private final YhMarketRepository yhMarketRepository;
    private final YhStockMReturnRateRepository yhStockMReturnRateRepository;
    private final InfostockSectorIndexRepository infostockSectorIndexRepository;
    private final InfostockSectorReturnRateMRepository infostockSectorReturnRateMRepository;
    private final YhEcoCloseRepository yhEcoCloseRepository;
    private final YhEcoMReturnRateRepository yhEcoMReturnRateRepository;

    public WidgetGroup6Generator(YhMarketRepository yhMarketRepository, YhStockMReturnRateRepository yhStockMReturnRateRepository, InfostockSectorIndexRepository infostockSectorIndexRepository, InfostockSectorReturnRateMRepository infostockSectorReturnRateMRepository, YhEcoCloseRepository yhEcoCloseRepository, YhEcoMReturnRateRepository yhEcoMReturnRateRepository) {
        this.yhMarketRepository = yhMarketRepository;
        this.yhStockMReturnRateRepository = yhStockMReturnRateRepository;
        this.infostockSectorIndexRepository = infostockSectorIndexRepository;
        this.infostockSectorReturnRateMRepository = infostockSectorReturnRateMRepository;
        this.yhEcoCloseRepository = yhEcoCloseRepository;
        this.yhEcoMReturnRateRepository = yhEcoMReturnRateRepository;
    }

    @Override
    public WidgetGroup getGroup() {
        return WidgetGroup.WIDGET_GROUP_6;
    }

    @Override
    protected WidgetResponse.Widget generateSpecificWidget(WidgetCreationDto dto, int widgetNo) {
        return switch (widgetNo) {
            case 18 -> generateWidget18(dto);
            default -> throw new IllegalStateException("Unexpected value: " + widgetNo);
        };
    }

    private WidgetResponse.Widget generateWidget18(WidgetCreationDto dto) {

        WidgetCreationDto.Entity entity = dto.entity().get(0);

        W18Dto sourceData = getWidget18Data(entity);
        if (ObjectUtils.isEmpty(sourceData)) return null;

        List<StockMonthlyReturn> stockMonthlyReturns = yhStockMReturnRateRepository.findByStockDtGoe("202201", "20231101");

        Map<StockCategory, List<StockMonthlyReturn>> stockCategoryListMap = StockCategory.categorizeStocks(stockMonthlyReturns);

        List<StockMonthlyReturn> largeStocks = stockCategoryListMap.get(StockCategory.LARGE);
        List<StockMonthlyReturn> mediumStocks = stockCategoryListMap.get(StockCategory.MEDIUM);
        List<StockMonthlyReturn> SmallStocks = stockCategoryListMap.get(StockCategory.SMALL);

        List<ItemRateValDto> large = largeStocks.stream().map(s -> new ItemRateValDto(s.stockCd(), s.stockNm(), s.stockDt(), s.returnRateM(), null)).toList();
        List<ItemRateValDto> middle = mediumStocks.stream().map(s -> new ItemRateValDto(s.stockCd(), s.stockNm(), s.stockDt(), s.returnRateM(), null)).toList();
        List<ItemRateValDto> small = SmallStocks.stream().map(s -> new ItemRateValDto(s.stockCd(), s.stockNm(), s.stockDt(), s.returnRateM(), null)).toList();

        return WidgetTemplateCreator.createWidget18Detail(sourceData, large, middle, small);

    }

    private W18Dto getWidget18Data(WidgetCreationDto.Entity entity) {
        return switch (Tag.findByName(entity.tag())) {
            case STOCK -> generateWidgetStock(entity.entity());
            case INDEX -> generateWidgetEco(entity.entity());
            case SECTOR -> generateWidgetSector(entity.entity());
            default -> throw new IllegalStateException("Unexpected value: " + entity.tag());
        };
    }

    private W18Dto generateWidgetStock(String stockNm) {

        List<YhStockMReturnRate> stockMReturn = yhStockMReturnRateRepository.findByStockNmAndStockDtGoe(stockNm, "202201");
        if (stockMReturn.isEmpty()) return null;

        return W18Dto.fromStock(stockMReturn);
    }

    private W18Dto generateWidgetSector(String sectorNm) {

        List<InfostockSectorReturnRateM> sectorMReturn = infostockSectorReturnRateMRepository.findByThemeNmAndThemeDtGoe(sectorNm, "202201");
        if (sectorMReturn.isEmpty()) return null;

        return W18Dto.fromSector(sectorMReturn);
    }

    private W18Dto generateWidgetEco(String ecoNm) {

        List<YhEcoMReturnRate> ecoMReturn = yhEcoMReturnRateRepository.findEcoNmAndEcoDtGoe(ecoNm, "202201");
        List<YhEcoClose> ecoMonthClose = yhEcoCloseRepository.findEcoNmAndStdDtGoe(ecoNm, "202201", "M");

        return W18Dto.fromEco(ecoMReturn, ecoMonthClose);
    }

}
