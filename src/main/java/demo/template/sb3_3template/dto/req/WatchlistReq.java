package demo.template.sb3_3template.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record WatchlistReq(

) {

    public record PostWatch(

            @Schema(description = "유저_ID", example = "123456")
            @NotEmpty
            String userId,

            @Schema(description = "마켓 타입코드", example = "STOCK", allowableValues = {"STOCK", "SECTOR", "INDEX"})
            @NotEmpty
            String marketCode,

            @Schema(description = "아이템_ID")
            @NotEmpty
            String itemId


    ) {

    }

    public record PatchWatch(

            @Schema(description = "유저_ID", example = "123456")
            @NotEmpty
            String userId,

            @Schema(description = "순서", example = "1")
            Integer position,

            @Schema(description = "기준일", example = "20240923")
            String standardDate

    ) {

    }

    public record DeleteWatch(

            @Schema(description = "유저_ID", example = "123456")
            @NotEmpty
            String userId

    ) {

    }

}
