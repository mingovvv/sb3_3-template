package demo.template.sb3_3template.enums;

import lombok.Getter;

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

    public static StockCategory fromMarketCap(long marketCap) {
        for (StockCategory category : StockCategory.values()) {
            if (marketCap >= category.minMarketCap && marketCap <= category.maxMarketCap) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid market cap value: " + marketCap);
    }

}
