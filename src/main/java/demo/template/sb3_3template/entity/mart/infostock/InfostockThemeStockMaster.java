package demo.template.sb3_3template.entity.mart.infostock;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(InfostockThemeStockMaster.CompositeKey.class)
@Entity
@Table(name = "infostock_theme_stock_master")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockThemeStockMaster {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_cd", referencedColumnName = "theme_cd")
    private InfostockThemeMaster infostockThemeMaster;

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Id
    @Column(name = "stock_cd")
    private String stockCd;

    @Column(name = "theme_nm")
    private String themeNm;

    @Column(name = "theme_in_reason")
    private String themeInReason;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String themeCd;
        private String stockCd;
    }

}
