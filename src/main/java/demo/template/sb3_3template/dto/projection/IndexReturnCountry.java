package demo.template.sb3_3template.dto.projection;

import com.querydsl.core.annotations.QueryProjection;

public record IndexReturnCountry(
        String ecoCd,
        String ecoNm,
        String stdDt,
        Double returnRate,
        String country
) {

    @QueryProjection
    public IndexReturnCountry(String ecoCd, String ecoNm, String stdDt, Double returnRate, String country) {
        this.ecoCd = ecoCd;
        this.ecoNm = ecoNm;
        this.stdDt = stdDt;
        this.returnRate = returnRate;
        this.country = country;
    }

}
