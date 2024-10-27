package demo.template.sb3_3template.entity.mart.infostock;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(InfostockSectorReturnRateM.CompositeKey.class)
@Entity
@Table(name = "infostock_sector_return_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockSectorReturnRateM {

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Column(name = "theme_nm")
    private String themeNm;

    @Id
    @Column(name = "theme_dt")
    private String themeDt;

    @Column(name = "return_rate")
    private Double returnRate;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String themeCd;
        private String themeDt;
    }

}
