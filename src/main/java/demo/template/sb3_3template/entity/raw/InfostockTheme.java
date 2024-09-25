package demo.template.sb3_3template.entity.raw;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "infostock_theme")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockTheme extends MartBaseEntity {

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Column(name = "theme_mm")
    private String themeNm;

    @Column(name = "theme_summary")
    private String themeSummary;

}
