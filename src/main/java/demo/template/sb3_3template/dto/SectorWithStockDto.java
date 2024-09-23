package demo.template.sb3_3template.dto;

import com.querydsl.core.annotations.QueryProjection;

public record SectorWithStockDto(

        String sectorId,

        String sectorName,

        String stockOfSector

) {

    @QueryProjection
    public SectorWithStockDto(String sectorId, String sectorName, String stockOfSector) {
        this.sectorId = sectorId;
        this.sectorName = sectorName;
        this.stockOfSector = stockOfSector;
    }

}

