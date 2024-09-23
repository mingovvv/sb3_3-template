package demo.template.sb3_3template.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record WatchlistRes(

        @Schema(description = "관심종목_ID", example = "1")
        Long watchlistId,

        @Schema(description = "마켓 타입코드", example = "STOCK", allowableValues = {"STOCK", "SECTOR", "INDEX"})
        String typeCode,

        @Schema(description = "아이템_ID", example = "15")
        String itemId,

        @Schema(description = "아이템 명", example = "삼성전자")
        String itemName,

        @Schema(description = "순서", example = "1")
        int sequence,

        @Schema(description = "기준일", example = "20240923")
        String standardDate,

        @Schema(description = "이벤트", example = "삼성정자 10만원 돌파")
        String event,

        @Schema(description = "지수", example = "KOSPI", allowableValues = {"KOSPI", "KOSDAQ"})
        String index,

        @Schema(description = "섹터", example = "반도체")
        String sector,

        @Schema(description = "등락률", example = "1")
        int fluctuationRate

) {

    @Builder
    public WatchlistRes(Long watchlistId, String typeCode, String itemId, String itemName, int sequence, String standardDate, String event, String index, String sector, int fluctuationRate) {
        this.watchlistId = watchlistId;
        this.typeCode = typeCode;
        this.itemId = itemId;
        this.itemName = itemName;
        this.sequence = sequence;
        this.standardDate = standardDate;
        this.event = event;
        this.index = index;
        this.sector = sector;
        this.fluctuationRate = fluctuationRate;
    }

    //    public static WatchlistRes of() {
//        return WatchlistRes.builder()
//                .watchlistId
//    }

}
