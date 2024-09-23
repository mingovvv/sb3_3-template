package demo.template.sb3_3template.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;

public record WatchlistReq(

) {

    public record PostWatch(

            @Schema(description = "유저_ID", example = "123456")
            String userId,

            @Schema(description = "아이템_ID", example = "STOCK", allowableValues = {"STOCK", "SECTOR", "INDEX"})
            String typeId,

            @Schema(description = "아이템 명", example = "삼성전자")
            String typeName,

            @Schema(description = "순서", example = "1")
            int sequence,

            @Schema(description = "기준일", example = "20240923")
            String standardDate,

            @Schema(description = "이벤트", example = "삼성정자 10만원 돌파")
            String event,

            @Schema(description = "섹터", example = "KOSPI", allowableValues = {"KOSPI", "KOSDAQ"})
            String sector,

            @Schema(description = "등락률", example = "1")
            int fluctuationRate

    ) {

    }

    public record PatchWatch(

            @Schema(description = "유저_ID", example = "123456")
            String userId,

            @Schema(description = "아이템_ID", example = "STOCK", allowableValues = {"STOCK", "SECTOR", "INDEX"})
            String typeId,

            @Schema(description = "아이템 명", example = "삼성전자")
            String typeName,

            @Schema(description = "순서", example = "1")
            int sequence,

            @Schema(description = "기준일", example = "20240923")
            String standardDate,

            @Schema(description = "이벤트", example = "삼성정자 10만원 돌파")
            String event,

            @Schema(description = "섹터", example = "KOSPI", allowableValues = {"KOSPI", "KOSDAQ"})
            String sector,

            @Schema(description = "등락률", example = "1")
            int fluctuationRate

    ) {

    }

}
