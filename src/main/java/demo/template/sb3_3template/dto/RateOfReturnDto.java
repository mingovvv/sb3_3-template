package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;

public record RateOfReturnDto(

        String code,
        int standardDateClose,
        int targetDateClose,
        int rateOfReturn

) {

    @QueryProjection
    public RateOfReturnDto(String code, int standardDateClose, int targetDateClose, int rateOfReturn) {
        this.code = code;
        this.standardDateClose = standardDateClose;
        this.targetDateClose = targetDateClose;
        this.rateOfReturn = rateOfReturn;
    }

}
