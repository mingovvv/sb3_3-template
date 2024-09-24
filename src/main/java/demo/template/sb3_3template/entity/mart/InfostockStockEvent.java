package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(InfostockStockEvent.CompositeKey.class)
@Entity
@Table(name = "infostock_stock_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockStockEvent extends MartBaseEntity {

    @Id
    @Column(name = "seq_id")
    private String seqId;

    @Id
    private String flag;

    @Column(name = "hist_dt")
    private String histDt;

    @Id
    @Column(name = "stock_cd")
    private String stockCd;

    @Column(name = "stock_hist")
    private String stockHist;

    @Column(name = "doc_id")
    private String docId;

    private String event;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String seqId;
        private String flag;
        private String stockCd;
    }

}
