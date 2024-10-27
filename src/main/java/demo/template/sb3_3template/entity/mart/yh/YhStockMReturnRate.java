package demo.template.sb3_3template.entity.mart.yh;

import demo.template.sb3_3template.entity.mart.YhStockCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(YhStockMReturnRate.CompositeKey.class)
@Entity
@Table(name = "yh_stock_m_return_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhStockMReturnRate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "isin", referencedColumnName = "isin"),
            @JoinColumn(name = "stock_cd", referencedColumnName = "stock_cd")
    })
    private YhStockCode yhStockCode;

    @Id
    private String isin;

    @Id
    @Column(name = "stock_cd")
    private String stockCd;

    @Column(name = "stock_dt")
    private String stockDt;

    @Column(name = "return_rate_m")
    private Double returnRateM;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String isin;
        private String stockCd;
        private String stockDt;
    }

}
