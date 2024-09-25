package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@IdClass(YhStockCode.CompositeKey.class)
@Entity
@Table(name = "yh_stock_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhStockCode extends MartBaseEntity {

    @Id
    private String isin;

    @Id
    @Column(name = "stock_cd")
    private String stockCd;

    @Column(name = "securities_cd")
    private String securitiesCd;

    @Column(name = "excng_id")
    private String excngId;

    @Column(name = "stock_mame_kr")
    private String stockNameKr;

    @Column(name = "sotck_nm_en")
    private String sotckNmEn;

    @Column(name = "industry")
    private String industry;

    @OneToMany(mappedBy = "yhStockCode")
    private List<YhStockReturnRate> yhStockReturnRates = new ArrayList<>();

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String isin;
        private String stockCd;
    }

}
