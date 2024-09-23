package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "infostock_theme_stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockThemeStock extends MartBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "std_dt", referencedColumnName = "std_dt"),
            @JoinColumn(name = "theme_cd", referencedColumnName = "theme_cd")
    })
    private InfostockTheme infostockTheme;

    @Id
    @Column(name = "stock_cd")
    private String stockCd;

    @Column(name = "theme_in_reason")
    private String themeInReason;

}