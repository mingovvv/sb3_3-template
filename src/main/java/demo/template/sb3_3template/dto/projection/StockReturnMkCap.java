package demo.template.sb3_3template.dto.projection;

import com.querydsl.core.annotations.QueryProjection;

public record StockReturnMkCap(
        String stockCd,
        String stockNm,
        String stdDt,
        Double returnRate,
        String idxCalMkCap
) {

    @QueryProjection
    public StockReturnMkCap(String stockCd, String stockNm, String stdDt, Double returnRate, String idxCalMkCap) {
        this.stockCd = stockCd;
        this.stockNm = stockNm;
        this.stdDt = stdDt;
        this.returnRate = returnRate;
        this.idxCalMkCap = idxCalMkCap;
    }

}
