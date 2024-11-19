package demo.template.sb3_3template.dto.projection;

import com.querydsl.core.annotations.QueryProjection;

public record StockThemeMkCap(
        String stockCd,
        String themeNm,
        String idxCalMkCap
) {

    @QueryProjection
    public StockThemeMkCap(String stockCd, String themeNm, String idxCalMkCap) {
        this.stockCd = stockCd;
        this.themeNm = themeNm;
        this.idxCalMkCap = idxCalMkCap;
    }

}
