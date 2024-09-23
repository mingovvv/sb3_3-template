package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(InfostockSectorEvent.CompositeKey.class)
@Entity
@Table(name = "infostock_stock_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockSectorEvent extends MartBaseEntity {

    @Id
    @Column(name = "theme_id")
    private String themeId;

    @Column(name = "hist_dt")
    private String histDt;

    @Id
    @Column(name = "theme_cd")
    private String themeCd;

    @Column(name = "theme_hist")
    private String themeHist;

    @Column(name = "doc_id")
    private String docId;

    private String event;

    @NoArgsConstructor
    @EqualsAndHashCode
    static protected class CompositeKey implements Serializable {
        private String themeId;
        private String themeCd;
    }

}
