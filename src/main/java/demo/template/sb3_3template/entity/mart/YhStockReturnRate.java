package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(YhStockReturnRate.CompositeKey.class)
@Entity
@Table(name = "stock_return_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhStockReturnRate extends MartBaseEntity {

//    @Id
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

    @Id
    @Column(name = "bsns_days", columnDefinition = "TINYINT")
    private Integer bsnsDays;

    @Id
    @Column(name = "std_dt")
    private String stdDt;

    @Column(name = "cmp_dt")
    private String cmpDt;

    @Column(name = "return_rate")
    private Double returnRate;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String isin;
        private String stockCd;
        private Integer bsnsDays;
        private String stdDt;
    }

}
