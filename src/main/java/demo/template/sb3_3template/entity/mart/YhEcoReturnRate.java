package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(YhEcoReturnRate.CompositeKey.class)
@Getter
@Entity
@Table(name = "yh_eco_return_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhEcoReturnRate extends MartBaseEntity {

    @ManyToOne
    @JoinColumn(name = "eco_cd", referencedColumnName = "eco_cd")
    private YhEcoCode yhEcoCode;

    @Id
    @Column(name = "bsns_days")
    private String bsnsDays;

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
        private String bsnsDays;
        private String stdDt;
    }

}
