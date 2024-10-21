package demo.template.sb3_3template.controller;

import demo.template.sb3_3template.dto.StockWithMaxThemeDto;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.entity.mart.YhStockReturnRate;
import demo.template.sb3_3template.repository.WatchlistRepository;
import demo.template.sb3_3template.repository.mart.YhMarketRepository;
import demo.template.sb3_3template.repository.mart.YhStockCodeRepository;
import demo.template.sb3_3template.repository.mart.YhStockReturnRateRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class TestController {

    private final YhMarketRepository yhMarketRepository;
    private final YhStockReturnRateRepository yhStockReturnRateRepository;
    private final WatchlistRepository watchlistRepository;
    private final YhStockCodeRepository yhStockCodeRepository;

    public TestController(YhMarketRepository yhMarketRepository, YhStockReturnRateRepository yhStockReturnRateRepository, WatchlistRepository watchlistRepository, YhStockCodeRepository yhStockCodeRepository) {
        this.yhMarketRepository = yhMarketRepository;
        this.yhStockReturnRateRepository = yhStockReturnRateRepository;
        this.watchlistRepository = watchlistRepository;
        this.yhStockCodeRepository = yhStockCodeRepository;
    }

    @GetMapping("/test")
    public String test() {
        List<StockWithMaxThemeDto> stocksWithMaxCapTheme = yhStockCodeRepository.getStocksWithMaxCapTheme();
        return "ok";
    }

    @GetMapping("/valid-session")
    public String validSession(Principal principal) {
//        log.info("principal: {}", principal.getName());
        return "/valid-session";
    }

    @GetMapping("/invalid-session")
    public String invalidSession(Principal principal) {
//        log.info("principal: {}", principal.getName());
        return "/invalid-session";
    }

    @GetMapping("/20241010")
    @Transactional(readOnly = true)
    public String test1() {

        try {

            Optional<YhStockReturnRate> test = yhStockReturnRateRepository.findTest("SS001", "isin001", "20241013", 20);


            Optional<YhStockReturnRate> test2 = yhStockReturnRateRepository.findTest("LG001", "isin002", "20241013", 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/20241010-1")
    @Transactional
    public String test2(@RequestBody TestDto testDto) {

        try {
            watchlistRepository.updateTest(testDto.getUserId(), testDto.getTtt());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Getter
    public static class TestDto {
        private String userId;
        private List<TTT> ttt;

        @Getter
        public static class TTT {
            private Long watchListId;
            private String stdDate;
            int position;

            @Override
            public String toString() {
                return "TTT{" +
                        "watchListId=" + watchListId +
                        ", stdDate='" + stdDate + '\'' +
                        ", position=" + position +
                        '}';
            }
        }

    }

}
