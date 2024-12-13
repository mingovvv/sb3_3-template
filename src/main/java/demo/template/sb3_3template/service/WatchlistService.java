package demo.template.sb3_3template.service;

import demo.template.common.enums.ResultCode;
import demo.template.common.exception.AppErrorException;
import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.EventOfSectorDto;
import demo.template.sb3_3template.dto.EventOfStockDto;
import demo.template.sb3_3template.dto.RateOfReturnDto;
import demo.template.sb3_3template.dto.projection.StockThemeMkCap;
import demo.template.sb3_3template.dto.req.WatchlistReq;
import demo.template.sb3_3template.dto.res.MarketRes;
import demo.template.sb3_3template.dto.res.UserWatchlistRes;
import demo.template.sb3_3template.dto.res.WatchlistRes;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.YhStockCode;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.YhEcoCode;
import demo.template.sb3_3template.entity.raw.InfostockTheme;
import demo.template.sb3_3template.enums.MarketType;
import demo.template.sb3_3template.repository.WatchlistRepository;
import demo.template.sb3_3template.repository.mart.*;
import demo.template.sb3_3template.repository.mart.infostock.InfostockSectorIndexRepository;
import demo.template.sb3_3template.repository.raw.InfostockThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    private final YhEcoReturnRateRepository yhEcoReturnRateRepository;

    public WatchlistService(YhStockCodeRepository yhStockCodeRepository, YhEcoCodeRepository yhEcoCodeRepository, InfostockThemeRepository infostockThemeRepository, InfostockSectorIndexRepository infostockSectorIndexRepository, WatchlistRepository watchlistRepository, InfostockStockEventRepository infostockStockEventRepository, InfostockSectorEventRepository infostockSectorEventRepository, YhEcoReturnRateRepository yhEcoReturnRateRepository) {
        this.yhStockCodeRepository = yhStockCodeRepository;
        this.yhEcoCodeRepository = yhEcoCodeRepository;
        this.infostockThemeRepository = infostockThemeRepository;
        this.infostockSectorIndexRepository = infostockSectorIndexRepository;
        this.watchlistRepository = watchlistRepository;
        this.infostockStockEventRepository = infostockStockEventRepository;
        this.infostockSectorEventRepository = infostockSectorEventRepository;
        this.yhEcoReturnRateRepository = yhEcoReturnRateRepository;
    }

    /**
     * 마켓 데이터(종목, 지수, 섹터) 조회
     *
     * @param marketType marketType
     * @return MarketRes
     */
    public MarketRes getMarketList(MarketType marketType) {

        return switch (marketType) {
            case STOCK -> {

                // 전체 종목 리스트 조회 + 종목의 섹터, 종목의 지수
                List<YhStockCode> stockCode = yhStockCodeRepository.findStockWithIndexAndSector(null);
                List<StockThemeMkCap> stockThemeMkCap = infostockSectorIndexRepository.findStockThemeByMkCap(null);

                Map<String, StockThemeMkCap> stockMainSector = stockThemeMkCap.stream()
                        .collect(Collectors.toMap(StockThemeMkCap::stockCd, Function.identity(), (v1, v2) -> Double.parseDouble(v1.idxCalMkCap()) > Double.parseDouble(v2.idxCalMkCap()) ? v1 : v2));

                yield MarketRes.fromStockCompositeDto(stockCode, stockMainSector);

            }
            case INDEX -> {

                // 전체 지수 리스트 조회 todo index 구분자 확인필
                List<YhEcoCode> indexs = yhEcoCodeRepository.findByType("index");
                yield MarketRes.fromYhEcoCode(indexs);
            }
            case SECTOR -> {

                // 전체 섹터 리스트 조회
                List<InfostockTheme> sectors = infostockThemeRepository.findAll();
                List<InfostockSectorIndex> recommendation = infostockSectorIndexRepository.findTop10ByStdDtOrderByIdxCalMkCapAsc(DateUtil.getMinusDay(0));
                yield MarketRes.fromInfostockSectorIndex(sectors, recommendation);
            }

        };

    }

    /**
     * 사용자 관심종목 리스트 조회
     *
     * @param userId userId
     * @return List<UserWatchlistRes>
     */
    public List<UserWatchlistRes> getUserWatchlist(String userId) {

        List<UserWatchlistRes> totalWatchlist = new ArrayList<>();

        List<Watchlist> watchlist = null;

        // 관심종목 마켓별로 구분
        Map<String, List<Watchlist>> grouped = watchlist.stream().collect(Collectors.groupingBy(Watchlist::getTypeCode));

        grouped.forEach((k, v) -> {

            if (k.equalsIgnoreCase(MarketType.STOCK.name())) {

                List<String> stockIds = v.stream().map(Watchlist::getItemId).toList();

                // 종목의 지수, 종목의 섹터 구하기
                List<YhStockCode> stockCode = yhStockCodeRepository.findStockWithIndexAndSector(stockIds);
                List<StockThemeMkCap> stockThemeMkCap = infostockSectorIndexRepository.findStockThemeByMkCap(stockIds);

                Map<String, StockThemeMkCap> stockMainSector = stockThemeMkCap.stream()
                        .collect(Collectors.toMap(StockThemeMkCap::stockCd, Function.identity(), (v1, v2) -> Double.parseDouble(v1.idxCalMkCap()) > Double.parseDouble(v2.idxCalMkCap()) ? v1 : v2));

//                Map<String, StockCompositeDto> stockMap = stockCode.stream().map(stock -> StockCompositeDto.from(stock, stockMainSector))

                // 이벤트 구하기
                List<EventOfStockDto> eventOfStock = infostockStockEventRepository.findEventOfStock(stockIds);
                Map<String, String> eventMap = eventOfStock.stream().collect(Collectors.toMap(EventOfStockDto::stockCode, EventOfStockDto::event));

                // 증감율 구하기
                List<RateOfReturnDto> rateOfReturn = yhStockCodeRepository.findStockRateOfReturn(v, DateUtil.getMinusDay(1));
                Map<String, Integer> rateMap = rateOfReturn.stream().collect(Collectors.toMap(RateOfReturnDto::code, RateOfReturnDto::rateOfReturn));

                v.stream().map(watch -> UserWatchlistRes.from(watch, null, eventMap, rateMap)).forEach(totalWatchlist::add);

            } else if (k.equalsIgnoreCase(MarketType.INDEX.name())) {

                // 증감율 구하기
                List<RateOfReturnDto> rateOfReturn = yhEcoCodeRepository.findIndexRateOfReturn(v, DateUtil.getMinusDay(1), "날짜타입");
                Map<String, Integer> rateMap = rateOfReturn.stream().collect(Collectors.toMap(RateOfReturnDto::code, RateOfReturnDto::rateOfReturn));

                v.stream().map(watch -> UserWatchlistRes.from(watch, rateMap)).forEach(totalWatchlist::add);

            } else {

                List<String> sectorCodeList = v.stream().map(Watchlist::getItemId).toList();

                // 이벤트 구하기
                List<EventOfSectorDto> eventOfSector = infostockSectorEventRepository.findEventOfSector(sectorCodeList);
                Map<String, String> eventMap = eventOfSector.stream().collect(Collectors.toMap(EventOfSectorDto::sectorCode, EventOfSectorDto::event));

                // 증감율 구하기
                List<RateOfReturnDto> rateOfReturn = infostockSectorIndexRepository.findSectorRateOfReturn(v, DateUtil.getMinusDay(1));
                Map<String, Integer> rateMap = rateOfReturn.stream().collect(Collectors.toMap(RateOfReturnDto::code, RateOfReturnDto::rateOfReturn));

                v.stream().map(watch -> UserWatchlistRes.from(watch, eventMap, rateMap)).forEach(totalWatchlist::add);

            }

        });

        return List.of();

    }

    /**
     * 사용자 관심종목 등록
     *
     * @param postWatch dto
     * @return WatchlistRes.PostWatch
     */
    @Transactional
    public WatchlistRes.PostWatch postUserWatchlist(WatchlistReq.PostWatch postWatch) {

        String itemNm = switch (MarketType.findByType(postWatch.marketCode())) {
            case STOCK -> yhStockCodeRepository.findByStockCd(postWatch.itemId())
                    .orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'itemId' does not exist.")).getStockNameKr();
            case INDEX -> yhEcoCodeRepository.findByTypeAndEcoCode("index", postWatch.itemId())
                    .orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'itemId' does not exist.")).getEcoNameKr();
            case SECTOR -> infostockThemeRepository.findById(postWatch.itemId())
                    .orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'itemId' does not exist.")).getThemeNm();
        };

        watchlistRepository.findByUserIdAndTypeCodeAndItemId(postWatch.userId(), postWatch.marketCode(), postWatch.itemId())
                .ifPresent(watchlist -> {
                    throw AppErrorException.of(ResultCode.Error.INVALID_VALUE, "This is the value already included in the watchlist.");
                });

        int maxPosition = watchlistRepository.getMaxPosition(postWatch.userId());
        return WatchlistRes.PostWatch.toRes(Watchlist.toEntity(postWatch, maxPosition, itemNm));

    }

    /**
     * 사용자 관심종목 수정
     *
     * @param watchlistId id
     * @param patchWatch  dto
     * @return List<UserWatchlistRes>
     */
    @Transactional
    public WatchlistRes.PostWatch patchUserWatchlist(Long watchlistId, WatchlistReq.PatchWatch patchWatch) {

        Watchlist watchlist = watchlistRepository.findById(watchlistId)
                .orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'watchlistId' does not exist."));

        // todo 기준일 validation 필요...? (미래, 형식)

        watchlistRepository.updateWatchlistPosition(watchlistId, patchWatch.userId(), patchWatch.standardDate(), watchlist.getPosition(), patchWatch.position());

        return WatchlistRes.PostWatch.toRes(watchlist, patchWatch);

    }

    /**
     * 사용자 관심종목 삭제
     *
     * @param watchlistId id
     * @param deleteWatch dto
     * @return WatchlistRes.DeleteWatch
     */
    @Transactional
    public WatchlistRes.DeleteWatch deleteUserWatchlist(Long watchlistId, WatchlistReq.DeleteWatch deleteWatch) {

        Watchlist watchlist = watchlistRepository.findByWatchlistIdAndUserId(watchlistId, deleteWatch.userId())
                .orElseThrow(() -> AppErrorException.of(ResultCode.Error.INVALID_VALUE, "The 'userId' and the 'watchlistId' do not match."));

        watchlistRepository.delete(watchlist);

        return WatchlistRes.DeleteWatch.toRes(watchlistId);

    }

}
