package demo.template.sb3_3template.dto.projection;

import com.querydsl.core.annotations.QueryProjection;

public record StockInSectorDto(
        String themeCd, // 테마코드
        String themeNm, // 테마명
        String stockCd, // 종목코드
        String stockNm, // 종목명
        String themeSummary, // 테마요약
        String stockSummary, // 기업요약
        String idxCalMkCap // 시총
) {

    @QueryProjection
    public StockInSectorDto(String themeCd, String themeNm, String stockCd, String stockNm, String themeSummary, String stockSummary, String idxCalMkCap) {
        this.themeCd = themeCd;
        this.themeNm = themeNm;
        this.stockCd = stockCd;
        this.stockNm = stockNm;
        this.themeSummary = themeSummary;
        this.stockSummary = stockSummary;
        this.idxCalMkCap = idxCalMkCap;
    }

}
