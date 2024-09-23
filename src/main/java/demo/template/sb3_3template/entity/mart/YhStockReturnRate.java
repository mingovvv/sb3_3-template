package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(YhStockReturnRate.CompositeKey.class)
@Entity
@Table(name = "stock_return_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhStockReturnRate extends MartBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "isin", referencedColumnName = "isin"),
            @JoinColumn(name = "stock_cd", referencedColumnName = "stock_cd")
    })
    private YhStockCode yhStockCode;

    @Id
    @Column(name = "bsns_days")
    private Byte bsnsDays;

    @Id
    @Column(name = "std_dt")
    private String stdDt;

    @Column(name = "cmp_dt")
    private String cmpDt;

    @Column(name = "return_rate")
    private String returnRate;

    @NoArgsConstructor
    @EqualsAndHashCode
    static protected class CompositeKey implements Serializable {
        private Byte bsnsDays;
        private String stdDt;
    }

}
