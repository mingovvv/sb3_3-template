package demo.template.sb3_3template.service;

import demo.template.common.enums.ResultCode;
import demo.template.common.exception.AppErrorException;
import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.EventOfSectorDto;
import demo.template.sb3_3template.dto.EventOfStockDto;
import demo.template.sb3_3template.dto.StockCompositeDto;
import demo.template.sb3_3template.dto.req.WatchlistReq;
import demo.template.sb3_3template.dto.res.MarketRes;
import demo.template.sb3_3template.dto.res.UserWatchlistRes;
import demo.template.sb3_3template.dto.res.WatchlistRes;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.YhEcoCode;
import demo.template.sb3_3template.enums.MarketType;
import demo.template.sb3_3template.repository.WatchlistRepository;
import demo.template.sb3_3template.repository.mart.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WatchlistService {

    private final YhStockCodeRepository yhStockCodeRepository;
    private final YhEcoCodeRepository yhEcoCodeRepository;
    private final InfostockThemeRepository infostockThemeRepository;
    private final InfostockSectorIndexRepository infostockSectorIndexRepository;
    private final WatchlistRepository watchlistRepository;
    private final InfostockStockEventRepository infostockStockEventRepository;
    private final InfostockSectorEventRepository infostockSectorEventRepository;

    public WatchlistService(YhStockCodeRepository yhStockCodeRepository, YhEcoCodeRepository yhEcoCodeRepository, InfostockThemeRepository infostockThemeRepository, InfostockSectorIndexRepository infostockSectorIndexRepository, WatchlistRepository watchlistRepository, InfostockStockEventRepository infostockStockEventRepository, InfostockSectorEventRepository infostockSectorEventRepository) {
        this.yhStockCodeRepository = yhStockCodeRepository;
        this.yhEcoCodeRepository = yhEcoCodeRepository;
        this.infostockThemeRepository = infostockThemeRepository;
        this.infostockSectorIndexRepository = infostockSectorIndexRepository;
        this.watchlistRepository = watchlistRepository;
        this.infostockStockEventRepository = infostockStockEventRepository;
        this.infostockSectorEventRepository = infostockSectorEventRepository;
    }

    public MarketRes getMarketList(MarketType marketType) {

        return switch (marketType) {
            case STOCK -> {

                // 전체 종목 리스트 조회 + 종목의 섹터, 종목의 지수
                List<StockCompositeDto> stockComposite = yhStockCodeRepository.findStockWithIndexAndSector(null);
                yield MarketRes.fromStockCompositeDto(stockComposite);

            }
            case INDEX -> {

                // 전체 지수 리스트 조회 todo index 구분자 확인필
                List<YhEcoCode> indexs = yhEcoCodeRepository.findByType("index");
                yield MarketRes.fromYhEcoCode(indexs);
            }
            case SECTOR -> {

                // 전체 섹터 리스트 조회
                List<InfostockSectorIndex> sectors = infostockSectorIndexRepository.findByStdDt(DateUtil.getMinusDay(1));
                yield MarketRes.fromInfostockSectorIndex(sectors);
            }

        };

    }

    public List<UserWatchlistRes> getUserWatchlist(String userId) {
        List<Watchlist> watchlist = watchlistRepository.findByUserId(userId);

        // 관심종목 마켓별로 구분
        Map<String, List<Watchlist>> grouped = watchlist.stream().collect(Collectors.groupingBy(Watchlist::getTypeCode));

        grouped.forEach((k, v) -> {

            if (k.equalsIgnoreCase("STOCK")) {

                List<String> stockCodeList = v.stream().map(Watchlist::getItemId).toList();

                // 동적쿼리로 변환
                List<StockCompositeDto> stockWithIndexAndSector = yhStockCodeRepository.findStockWithIndexAndSector(stockCodeList);

                // 이벤트 구하기
                List<EventOfStockDto> stockWithEvent = infostockStockEventRepository.findEventOfStock(stockCodeList);

                // 증감율 구하기
                // todo


            } else if (k.equalsIgnoreCase("INDEX")) {

                // 증감율 구하기
                // todo

            } else {

                List<String> sectorCodeList = v.stream().map(Watchlist::getItemId).toList();

                // 이벤트 구하기
                List<EventOfSectorDto> stockWithEvent = infostockSectorEventRepository.findEventOfSector(sectorCodeList);

                // 증감율 구하기
                // todo

            }

        });

        return UserWatchlistRes.from(watchlist);

    }

    @Transactional
    public WatchlistRes.PostWatch postUserWatchlist(WatchlistReq.PostWatch postWatch) {

        switch (MarketType.findByType(postWatch.marketCode())) {
            case STOCK -> yhStockCodeRepository.findByStockCd(postWatch.itemId()).orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'itemId' does not exist."));
            case INDEX -> yhEcoCodeRepository.findByTypeAndEcoCode("index", postWatch.itemId()).orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'itemId' does not exist."));
            // todo 섹터 정규화된 테이블 있는지 확인
        }

        watchlistRepository.findByUserIdAndTypeCodeAndItemId(postWatch.userId(), postWatch.marketCode(), postWatch.itemId())
                .ifPresent(watchlist -> {
                    throw AppErrorException.of(ResultCode.Error.INVALID_VALUE, "This is the value already included in the watchlist.");
                });

        int maxPosition = watchlistRepository.getMaxPosition(postWatch.userId());
        return WatchlistRes.PostWatch.toRes(Watchlist.toEntity(postWatch, maxPosition));

    }

    @Transactional
    public List<UserWatchlistRes> patchUserWatchlist(Long watchlistId, WatchlistReq.PatchWatch patchWatch) {

        Watchlist watchlist = watchlistRepository.findById(watchlistId)
                .orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'watchlistId' does not exist."));

        Integer previousPosition = watchlist.getPosition();

        // todo 기준일 validation 필요 (미래, 형식)

        if (patchWatch.position() != null) {

            if (previousPosition > patchWatch.position()) {
//                watchlistRepository.updatePositionByUserId(1, patchWatch.userId());
            }
            if (previousPosition < patchWatch.position()) {
//                watchlistRepository.updatePositionByUserId(-1, patchWatch.userId());
            }

            watchlist.updateStandardDate(patchWatch);

        }


        return null;

    }

    @Transactional
    public WatchlistRes.DeleteWatch deleteUserWatchlist(Long watchlistId, WatchlistReq.DeleteWatch deleteWatch) {

        Watchlist watchlist = watchlistRepository.findByWatchlistIdAndUserId(watchlistId, deleteWatch.userId())
                .orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'userId' and the 'watchlistId' do not match."));

        watchlistRepository.delete(watchlist);

        return WatchlistRes.DeleteWatch.toRes(watchlistId);
    }

}
