package demo.template.sb3_3template.entity.mart.infostock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "infostock_theme_master")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockThemeMaster {

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Column(name = "theme_nm")
    private String themeNm;

    @Column(name = "theme_summary")
    private String ThemeSummary;

}
