package demo.template.sb3_3template.entity.raw;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "infostock_theme_stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockThemeStock extends MartBaseEntity {

    @Id
    @Column(name = "stock_cd")
    private String stockCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_cd", referencedColumnName = "theme_cd")
    private InfostockTheme infostockTheme;

    @Column(name = "theme_in_reason")
    private String themeInReason;

}