package demo.template.sb3_3template.dto.projection;

import com.querydsl.core.annotations.QueryProjection;

public record StockMonthlyReturn(
        String stockCd,
        String stockNm,
        String stockDt,
        Double returnRateM,
        String idxCalMkCap
) {

    @QueryProjection
    public StockMonthlyReturn(String stockCd, String stockNm, String stockDt, Double returnRateM, String idxCalMkCap) {
        this.stockCd = stockCd;
        this.stockNm = stockNm;
        this.stockDt = stockDt;
        this.returnRateM = returnRateM;
        this.idxCalMkCap = idxCalMkCap;
    }

}
