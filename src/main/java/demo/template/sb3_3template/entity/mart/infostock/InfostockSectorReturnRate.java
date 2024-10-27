package demo.template.sb3_3template.entity.mart.infostock;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(InfostockSectorReturnRate.CompositeKey.class)
@Entity
@Table(name = "infostock_sector_return_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockSectorReturnRate {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "theme_cd", referencedColumnName = "theme_cd"),
            @JoinColumn(name = "std_dt", referencedColumnName = "std_dt")
    })
    private InfostockSectorIndex infostockSectorIndex;

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Id
    @Column(name = "std_dt")
    private String stdDt;

    @Id
    @Column(name = "bsns_days")
    private Integer bsnsDays;

    @Column(name = "cap_dt")
    private String capDt;

    @Column(name = "return_rate")
    private Double returnRate;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private Integer bsnsDays;
        private String themeCd;
        private String stdDt;
    }

}
