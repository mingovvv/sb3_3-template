package demo.template.sb3_3template.entity.mart.infostock;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(InfostockSectorIndex.CompositeKey.class)
@Entity
@Table(name = "infostock_sector_index")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockSectorIndex extends MartBaseEntity {

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Column(name = "theme_nm")
    private String themeNm;

    @Id
    @Column(name = "std_dt")
    private String stdDt;

    private String close;

    @Column(name = "idx_cal_mk_cap")
    private String idxCalMkCap;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String themeCd;
        private String stdDt;
    }

}
