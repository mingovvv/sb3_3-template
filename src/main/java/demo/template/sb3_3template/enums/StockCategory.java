package demo.template.sb3_3template.enums;

import demo.template.sb3_3template.dto.projection.StockMonthlyReturn;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum StockCategory {
    LARGE("대형", 1_000_000_000_000L, Long.MAX_VALUE),
    MEDIUM("중형", 500_000_000_000L, 999_999_999_999L),
    SMALL("소형", 0, 499_999_999_999L);

    private final String description;
    private final long minMarketCap;
    private final long maxMarketCap;

    StockCategory(String description, long minMarketCap, long maxMarketCap) {
        this.description = description;
        this.minMarketCap = minMarketCap;
        this.maxMarketCap = maxMarketCap;
    }

    public static Map<StockCategory, List<StockMonthlyReturn>> categorizeStocks(List<StockMonthlyReturn> stocks) {
        return stocks.stream()
                .collect(Collectors.groupingBy(stock -> {
                    long marketCap = StringUtils.isEmpty(stock.idxCalMkCap()) ? 0 : Long.parseLong(stock.idxCalMkCap());
                    if (marketCap >= LARGE.minMarketCap && marketCap <= LARGE.maxMarketCap) {
                        return LARGE;
                    } else if (marketCap >= MEDIUM.minMarketCap && marketCap <= MEDIUM.maxMarketCap) {
                        return MEDIUM;
                    } else {
                        return SMALL;
                    }
                }));
    }

}
