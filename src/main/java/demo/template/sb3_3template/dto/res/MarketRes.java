package demo.template.sb3_3template.dto.res;

import demo.template.sb3_3template.dto.StockCompositeDto;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.YhEcoCode;
import demo.template.sb3_3template.entity.raw.InfostockTheme;
import demo.template.sb3_3template.enums.IndexKrType;
import demo.template.sb3_3template.enums.MarketType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record MarketRes(

        @Schema(description = "마켓 타입코드", example = "STOCK", allowableValues = {"STOCK", "SECTOR", "INDEX"})
        String marketCode,

        List<MarketObject> marketObjects,

        @Schema(description = "추천 지수 또는 섹터 리스트", example = "S&P500")
        List<String> recommendation

) {

        public record MarketObject(

                @Schema(description = "아이템_ID", example = "15")
                String itemId,

                @Schema(description = "아이템 명", example = "삼성전자")
                String itemName,

                @Schema(description = "지수", example = "KOSPI", allowableValues = {"KOSPI", "KOSDAQ"})
                String index,

                @Schema(description = "섹터", example = "반도체")
                String sector

        ) {

                @Builder
                public MarketObject(String itemId, String itemName, String index, String sector) {
                        this.itemId = itemId;
                        this.itemName = itemName;
                        this.index = index;
                        this.sector = sector;
                }

                public static MarketObject fromYhEcoCode(YhEcoCode yhEcoCode) {
                        return MarketObject.builder()
                               .itemId(yhEcoCode.getEcoCode())
                               .itemName(yhEcoCode.getEcoNameKr())
                               .build();
                }

                public static MarketObject fromStockCompositeDto(StockCompositeDto stockCompositeDto) {
                        return MarketObject.builder()
                                .itemId(stockCompositeDto.stockId())
                                .itemName(stockCompositeDto.stockName())
                                .index(IndexKrType.findByExcngId(stockCompositeDto.indexOfStock()).name())
                                .sector(stockCompositeDto.sectorOfStock())
                                .build();
                }

                public static MarketObject fromInfostockSectorIndex(InfostockTheme infostockTheme) {
                        return MarketObject.builder()
                                .itemId(infostockTheme.getThemeCd())
                                .itemName(infostockTheme.getThemeNm())
                                .build();
                }

                public static List<MarketObject> fromYhEcoCode(List<YhEcoCode> yhEcoCodeList) {
                        return yhEcoCodeList.stream().map(MarketObject::fromYhEcoCode).toList();
                }

                public static List<MarketObject> fromStockCompositeDto(List<StockCompositeDto> stockComposite) {
                        return stockComposite.stream().map(MarketObject::fromStockCompositeDto).toList();
                }

                public static List<MarketObject> fromInfostockSectorIndex(List<InfostockTheme> sectors) {
                        return sectors.stream().map(MarketObject::fromInfostockSectorIndex).toList();
                }

        }

        @Builder
        public MarketRes(String marketCode, List<MarketObject> marketObjects, List<String> recommendation) {
                this.marketCode = marketCode;
                this.marketObjects = marketObjects;
                this.recommendation = recommendation;
        }

        public static MarketRes fromYhEcoCode(List<YhEcoCode> yhEcoCodeList) {
                return MarketRes.builder()
                        .marketCode(MarketType.INDEX.name())
                        .marketObjects(MarketObject.fromYhEcoCode(yhEcoCodeList))
                        .recommendation(yhEcoCodeList.stream().map(YhEcoCode::getEcoNameKr).toList())
                        .build();
        }

        public static MarketRes fromStockCompositeDto(List<StockCompositeDto> stockComposite) {
                return MarketRes.builder()
                        .marketCode(MarketType.STOCK.name())
                        .marketObjects(MarketObject.fromStockCompositeDto(stockComposite))
                        .build();
        }

        public static MarketRes fromInfostockSectorIndex(List<InfostockTheme> sectors, List<InfostockSectorIndex> recommendation) {
                return MarketRes.builder()
                        .marketCode(MarketType.SECTOR.name())
                        .marketObjects(MarketObject.fromInfostockSectorIndex(sectors))
                        .recommendation(findTop10Recommendation(recommendation))
                        .build();
        }

        private static List<String> findTop10Recommendation(List<InfostockSectorIndex> sectors) {
                return sectors.stream().map(InfostockSectorIndex::getThemeNm).toList();
//                return sectors.stream()
//                        .sorted((s1, s2) -> {
//                                BigDecimal value1 = new BigDecimal(s1.getIdxCalMkCap());
//                                BigDecimal value2 = new BigDecimal(s2.getIdxCalMkCap());
//                                return value2.compareTo(value1);
//                        })
//                       .limit(10)
//                       .map(InfostockSectorIndex::getThemeNm)
//                       .toList();
        }

}
