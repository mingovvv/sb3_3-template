package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;

public record StockCompositeDto(

        String stockId,

        String stockName,

        String indexWithStock,

        String sectorWithStock

) {

    @QueryProjection
    public StockCompositeDto(String stockId, String stockName, String indexWithStock, String sectorWithStock) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.indexWithStock = indexWithStock;
        this.sectorWithStock = sectorWithStock;
    }

}
