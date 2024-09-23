package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(InfostocStockEvent.CompositeKey.class)
@Entity
@Table(name = "infostock_stock_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostocStockEvent extends MartBaseEntity {

    @Id
    private String seqId;

    @Id
    private String flag;

    private String histDt;

    @Id
    private String stockCd;

    private String docId;

    private String event;

    @NoArgsConstructor
    @EqualsAndHashCode
    static protected class CompositeKey implements Serializable {
        private String seqId;
        private String flag;
        private String stockCd;
    }

}
