package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;

public record StockCompositeDto(

        String stockId,

        String stockName,

        String indexOfStock,

        String sectorOfStock

) {

    @QueryProjection
    public StockCompositeDto(String stockId, String stockName, String indexOfStock, String sectorOfStock) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.indexOfStock = indexOfStock;
        this.sectorOfStock = sectorOfStock;
    }

}
