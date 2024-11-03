package demo.template.sb3_3template.dto.projection;

import com.querydsl.core.annotations.QueryProjection;

public record SectorReturn(
        String sectorCd,
        String sectorNm,
        String stdDt,
        Double returnRate
) {

    @QueryProjection
    public SectorReturn(String sectorCd, String sectorNm, String stdDt, Double returnRate) {
        this.sectorCd = sectorCd;
        this.sectorNm = sectorNm;
        this.stdDt = stdDt;
        this.returnRate = returnRate;
    }

}
