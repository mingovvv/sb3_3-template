package demo.template.sb3_3template.entity.raw;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(YhHoliday.CompositeKey.class)
@Entity
@Table(name = "yh_holiday")
public class YhHoliday {

    @Id
    @Column(name = "cls_dt", length = 8, nullable = false)
    private String clsDt;

    @Id
    @Column(name = "contry_cd", length = 2, nullable = false)
    private String contryCd;

    @Column(name = "cls_y_m", length = 6, nullable = false)
    private String clsYM;

    @Column(name = "cls_dow_cd", length = 1, nullable = false)
    private String clsDowCd;

    @Column(name = "cls_dow_kr", length = 10, nullable = false)
    private String clsDowKr;

    @Column(name = "cls_nm", length = 128, nullable = false)
    private String clsNm;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String clsDt;
        private String contryCd;
    }

}
