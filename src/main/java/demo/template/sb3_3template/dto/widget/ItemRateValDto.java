package demo.template.sb3_3template.dto.widget;

public record ItemRateValDto(
        String itemCd,
        String itemNm,
        String yearMonth,
        double mRate,
        String val
) {

    public ItemRateValDto(String itemCd, String itemNm, String yearMonth, double mRate, String val) {
        this.itemCd = itemCd;
        this.itemNm = itemNm;
        this.yearMonth = yearMonth;
        this.mRate = mRate;
        this.val = val;
    }

}
