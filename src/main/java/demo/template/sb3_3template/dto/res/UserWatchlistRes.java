package demo.template.sb3_3template.dto.res;

import demo.template.sb3_3template.entity.Watchlist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record UserWatchlistRes(

        @Schema(description = "관심종목_ID", example = "1")
        Long watchlistId,

        @Schema(description = "마켓 타입코드", example = "STOCK", allowableValues = {"STOCK", "SECTOR", "INDEX"})
        String marketCode,

        @Schema(description = "아이템_ID", example = "15")
        String itemId,

        @Schema(description = "아이템 명", example = "삼성전자")
        String itemName,

        @Schema(description = "순서", example = "1")
        int position,

        @Schema(description = "기준일", example = "20240923")
        String standardDate,

        @Schema(description = "이벤트", example = "삼성정자 10만원 돌파")
        String event,

        @Schema(description = "지수", example = "KOSPI", allowableValues = {"KOSPI", "KOSDAQ"})
        String index,

        @Schema(description = "섹터", example = "반도체")
        String sector,

        @Schema(description = "등락률", example = "1")
        int rateOfReturn

) {

        @Builder
        public UserWatchlistRes(Long watchlistId, String marketCode, String itemId, String itemName, int position, String standardDate, String event, String index, String sector, int rateOfReturn) {
                this.watchlistId = watchlistId;
                this.marketCode = marketCode;
                this.itemId = itemId;
                this.itemName = itemName;
                this.position = position;
                this.standardDate = standardDate;
                this.event = event;
                this.index = index;
                this.sector = sector;
                this.rateOfReturn = rateOfReturn;
        }

        public static List<UserWatchlistRes> from(List<Watchlist> watchlist) {
                return null;
        }
}
