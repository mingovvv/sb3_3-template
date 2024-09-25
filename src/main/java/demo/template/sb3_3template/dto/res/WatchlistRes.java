package demo.template.sb3_3template.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import demo.template.sb3_3template.entity.Watchlist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

public record WatchlistRes(

) {

    public record PostWatch(

            @Schema(description = "유저_ID", example = "123456")
            String userId,

            @Schema(description = "관심종목_ID", example = "14")
            Long watchListId,

            @Schema(description = "마켓 타입코드", example = "STOCK", allowableValues = {"STOCK", "SECTOR", "INDEX"})
            String marketCode,

            @Schema(description = "아이템_ID")
            String itemId,

            @Schema(description = "아이템 명")
            String itemName,

            @Schema(description = "위치", example = "4")
            int position,

            @Schema(description = "기준일", example = "20240901")
            String standardDate,

            @Schema(description = "등록시간", example = "2024-09-24 14:20:00")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            LocalDateTime registerDt

    ) {

        @Builder
        public PostWatch(String userId, Long watchListId, String marketCode, String itemId, String itemName, int position, String standardDate, LocalDateTime registerDt) {
            this.userId = userId;
            this.watchListId = watchListId;
            this.marketCode = marketCode;
            this.itemId = itemId;
            this.itemName = itemName;
            this.position = position;
            this.standardDate = standardDate;
            this.registerDt = registerDt;
        }

        public static WatchlistRes.PostWatch toRes(Watchlist watchlist) {
            return WatchlistRes.PostWatch.builder()
                    .watchListId(watchlist.getWatchlistId())
                    .userId(watchlist.getUserId())
                    .marketCode(watchlist.getTypeCode())
                    .itemId(watchlist.getItemId())
                    .itemName(watchlist.getItemName())
                    .position(watchlist.getPosition())
                    .standardDate(watchlist.getStandardDate())
                    .registerDt(watchlist.getRegisterDt())
                    .build();
        }

    }

    public record DeleteWatch(

            @Schema(description = "관심종목_ID", example = "14")
            Long watchListId

    ) {

        @Builder
        public DeleteWatch(Long watchListId) {
            this.watchListId = watchListId;
        }

        public static WatchlistRes.DeleteWatch toRes(Long watchListId) {
            return WatchlistRes.DeleteWatch.builder()
                   .watchListId(watchListId)
                   .build();
        }

    }

}
