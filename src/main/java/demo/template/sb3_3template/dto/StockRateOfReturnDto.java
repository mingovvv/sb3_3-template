package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

public record StockRateOfReturnDto(

        String code,
        String excngId,
        Double returnRate

) {

    @Builder
    @QueryProjection
    public StockRateOfReturnDto(String code, String excngId, Double returnRate) {
        this.code = code;
        this.excngId = excngId;
        this.returnRate = returnRate;
    }

    public static StockRateOfReturnDto ofEmpty() {
        return StockRateOfReturnDto.builder().build();
    }

}
