package demo.template.sb3_3template.service;

import com.querydsl.core.group.GroupBy;
import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.StockCompositeDto;
import demo.template.sb3_3template.dto.req.WatchlistReq;
import demo.template.sb3_3template.dto.res.MarketRes;
import demo.template.sb3_3template.dto.res.UserWatchlistRes;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.InfostockSectorIndex;
import demo.template.sb3_3template.entity.mart.YhEcoCode;
import demo.template.sb3_3template.enums.MarketType;
import demo.template.sb3_3template.repository.WatchlistRepository;
import demo.template.sb3_3template.repository.mart.InfostockSectorIndexRepository;
import demo.template.sb3_3template.repository.mart.InfostockThemeRepository;
import demo.template.sb3_3template.repository.mart.YhEcoCodeRepository;
import demo.template.sb3_3template.repository.mart.YhStockCodeRepository;
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

    public WatchlistService(YhStockCodeRepository yhStockCodeRepository, YhEcoCodeRepository yhEcoCodeRepository, InfostockThemeRepository infostockThemeRepository, InfostockSectorIndexRepository infostockSectorIndexRepository, WatchlistRepository watchlistRepository) {
        this.yhStockCodeRepository = yhStockCodeRepository;
        this.yhEcoCodeRepository = yhEcoCodeRepository;
        this.infostockThemeRepository = infostockThemeRepository;
        this.infostockSectorIndexRepository = infostockSectorIndexRepository;
        this.watchlistRepository = watchlistRepository;
    }

    public MarketRes getMarketList(MarketType marketType) {

        return switch (marketType) {
            case STOCK -> {

                // 전체 종목 리스트 조회 + 종목의 섹터, 종목의 지수
                List<StockCompositeDto> stockComposite = yhStockCodeRepository.findAllStockWithIndexAndSector();
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

                // 동적쿼리로 변환
                List<StockCompositeDto> allStockWithIndexAndSector = yhStockCodeRepository.findAllStockWithIndexAndSector();

                // 이벤트 구하기

                // 증감율 구하기

            } else if (k.equalsIgnoreCase("INDEX")) {

                // 증감율 구하기

            } else {


                // 이벤트 구하기

                // 증감율 구하기

            }

        });

        return UserWatchlistRes.from(watchlist);

    }

    @Transactional
    public List<UserWatchlistRes> postUserWatchlist(WatchlistReq.PostWatch postWatch) {
        return null;
    }

    @Transactional
    public List<UserWatchlistRes> patchUserWatchlist(Long watchlistId, WatchlistReq.PostWatch postWatch) {
        return null;
    }

    @Transactional
    public List<UserWatchlistRes> deleteUserWatchlist(Long watchlistId, WatchlistReq.PostWatch postWatch) {
        return null;
    }

}
