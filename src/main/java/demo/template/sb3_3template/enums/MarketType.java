package demo.template.sb3_3template.enums;

public enum MarketType {

    STOCK,
    SECTOR,
    INDEX;

    public static MarketType findByType(String type) {
        return MarketType.valueOf(type.toUpperCase());
    }

}
