package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(InfostockTheme.CompositeKey.class)
@Entity
@Table(name = "infostock_theme")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockTheme extends MartBaseEntity  {

    @Id
    @Column(name = "std_dt")
    private String stdDt;

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Column(name = "theme_nm")
    private String themeNm;

    @Column(name = "theme_summary")
    private String themeSummary;

    @NoArgsConstructor
    @EqualsAndHashCode
    public static class CompositeKey implements Serializable {
        private String stdDt;
        private String themeCd;
    }

}
