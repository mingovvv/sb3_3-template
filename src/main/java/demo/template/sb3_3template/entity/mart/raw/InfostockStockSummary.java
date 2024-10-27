package demo.template.sb3_3template.entity.mart.raw;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "infostock_stock_summary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfostockStockSummary {

    @Id
    private String stockCd;

    @Column(name = "stock_summary")
    private String stockSummary;

}