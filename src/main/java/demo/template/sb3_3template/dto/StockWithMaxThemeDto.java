package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;

public class StockWithMaxThemeDto {

    private String stockCd;
    private String stockNmKr;
    private String themeNm;

    @QueryProjection
    public StockWithMaxThemeDto(String stockCd, String stockNmKr, String themeNm) {
        this.stockCd = stockCd;
        this.stockNmKr = stockNmKr;
        this.themeNm = themeNm;
    }

}
