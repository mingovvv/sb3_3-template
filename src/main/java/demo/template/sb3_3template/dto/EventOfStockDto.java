package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;

public record EventOfStockDto(

        String stockCode,
        String event

) {

    @QueryProjection
    public EventOfStockDto(String stockCode, String event) {
        this.stockCode = stockCode;
        this.event = event;
    }

}
