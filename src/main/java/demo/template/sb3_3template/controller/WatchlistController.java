package demo.template.sb3_3template.controller;

import demo.template.common.model.BaseResponse;
import demo.template.common.model.BaseResponseFactory;
import demo.template.sb3_3template.dto.req.WatchlistReq;
import demo.template.sb3_3template.dto.res.MarketRes;
import demo.template.sb3_3template.dto.res.UserWatchlistRes;
import demo.template.sb3_3template.enums.MarketType;
import demo.template.sb3_3template.service.WatchlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "01. 관심종목 관리", description = "관심종목 관리 API")
@RestController
@RequestMapping("/v1/qna")
public class WatchlistController {

    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }


    @Operation(summary = "마켓 데이터 목록 조회 API", description = "마켓 데이터(종목, 지수, 섹터)를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "HTTP Status Code is 200")
    @GetMapping("/marketlist")
    public BaseResponse<MarketRes> getMarketList(
            @RequestParam @Parameter(description = "조회 유형(STOCK, INDEX, SECTOR)", example = "SECTOR") @Valid MarketType type
    ) {
        return BaseResponseFactory.create(watchlistService.getMarketList(type));
    }

    @Operation(summary = "관심종목 조회 API", description = "유저의 관심종목을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "HTTP Status Code is 200")
    @GetMapping("/watchlist/users/{userId}")
    public BaseResponse<List<UserWatchlistRes>> getUserWatchlist(
            @PathVariable("userId") @Parameter(description = "유저_ID", example = "1") String userId
    ) {
        return BaseResponseFactory.create(watchlistService.getUserWatchlist(userId));
    }

    @Operation(summary = "관심종목 등록 API", description = "관심종목으로 등록합니다.")
    @ApiResponse(responseCode = "200", description = "HTTP Status Code is 200")
    @PostMapping("/watchlist")
    public BaseResponse<List<UserWatchlistRes>> postUserWatchlist(
            @RequestBody @Valid WatchlistReq.PostWatch postWatch
    ) {
        return BaseResponseFactory.create(watchlistService.postUserWatchlist(postWatch));
    }

    @Operation(summary = "관심종목 수정 API", description = "관심종목을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "HTTP Status Code is 200")
    @PatchMapping("/watchlist/{watchlist_id}")
    public BaseResponse<List<UserWatchlistRes>> patchUserWatchlist(
            @PathVariable("watchlist_id") @Parameter(description = "관심종목_ID", example = "1") Long watchlistId,
            @RequestBody @Valid WatchlistReq.PostWatch postWatch
    ) {
        return BaseResponseFactory.create(watchlistService.patchUserWatchlist(watchlistId, postWatch));
    }

    @Operation(summary = "관심종목 삭제 API", description = "관심종목을 제거합니다.")
    @ApiResponse(responseCode = "200", description = "HTTP Status Code is 200")
    @DeleteMapping("/watchlist/{watchlist_id}")
    public BaseResponse<List<UserWatchlistRes>> deleteUserWatchlist(
            @PathVariable("watchlist_id") @Parameter(description = "관심종목_ID", example = "1") Long watchlistId,
            @RequestBody @Valid WatchlistReq.PostWatch postWatch
    ) {
        return BaseResponseFactory.create(watchlistService.deleteUserWatchlist(watchlistId, postWatch));
    }

}
