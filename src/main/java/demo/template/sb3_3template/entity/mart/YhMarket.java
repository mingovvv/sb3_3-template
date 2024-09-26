package demo.template.sb3_3template.entity.mart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Entity
@Table(name = "yh_market")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhMarket {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "isin", referencedColumnName = "isin"),
            @JoinColumn(name = "stock_cd", referencedColumnName = "stock_cd")
    })
    private YhStockCode yhStockCode;

    @Id
    @Column(name = "std_dt")
    private String stdDt;

    private String base;

    private String open;

    private String high;

    private String low;

    private String close;

    @Column(name = "adjust_price")
    private String adjustPrice;

    @Column(name = "cum_vol")
    private String cumVol;

    @Column(name = "listed_cnt")
    private String listedCnt;

    @Column(name = "idx_cal_shr")
    private String idxCalShr;

    @Column(name = "mk_cap")
    private String mkCap;

    @Column(name = "idx_cal_mk_cap")
    private String idxCalMkCap;

    @Column(name = "divd_tot_amnt")
    private String divdTotAmnt;

    @Column(name = "divd_yield")
    private String divdYield;

}
