package demo.template.sb3_3template.entity.mart;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(ThemeStockMaster.CompositeKey.class)
@Entity
@Table(name = "theme_stock_master")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeStockMaster {

    @Id
    private String themeCd;
    @Id
    private String stockCd;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String themeCd;
        private String stockCd;
    }

}
