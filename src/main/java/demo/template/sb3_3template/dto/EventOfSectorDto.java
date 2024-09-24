package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;

public record EventOfSectorDto(

        String sectorCode,
        String event

) {

    @QueryProjection
    public EventOfSectorDto(String sectorCode, String event) {
        this.sectorCode = sectorCode;
        this.event = event;
    }

}
