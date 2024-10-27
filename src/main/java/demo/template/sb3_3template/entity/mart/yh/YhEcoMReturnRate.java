package demo.template.sb3_3template.entity.mart.yh;

import demo.template.sb3_3template.entity.mart.YhEcoCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(YhEcoMReturnRate.CompositeKey.class)
@Entity
@Table(name = "yh_eco_m_return_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhEcoMReturnRate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eco_cd", referencedColumnName = "eco_cd")
    private YhEcoCode yhEcoCode;

    @Id
    @Column(name = "eco_cd")
    private String ecoCode;

    @Id
    @Column(name = "eco_dt")
    private String ecoDt;

    @Column(name = "return_rate_m")
    private Double returnRateM;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String ecoCode;
        private String ecoDt;
    }

}
