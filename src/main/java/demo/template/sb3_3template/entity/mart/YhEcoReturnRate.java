package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@ToString
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
    @Column(name = "eco_cd")
    private String ecoCode;

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
        private String ecoCode;
        private Integer bsnsDays;
        private String stdDt;
    }

    @Builder
    public YhEcoReturnRate(YhEcoCode yhEcoCode, Integer bsnsDays, String stdDt, String cmpDt, Double returnRate) {
        this.yhEcoCode = yhEcoCode;
        this.bsnsDays = bsnsDays;
        this.stdDt = stdDt;
        this.cmpDt = cmpDt;
        this.returnRate = returnRate;
    }

    static public YhEcoReturnRate ofEmpty() {
        return YhEcoReturnRate.builder().build();
    }

}
