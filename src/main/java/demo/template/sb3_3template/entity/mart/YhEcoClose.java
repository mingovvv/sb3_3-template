package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@IdClass(YhEcoClose.CompositeKey.class)
@Entity
@Table(name = "yh_eco_close")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhEcoClose extends MartBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eco_cd", referencedColumnName = "eco_cd")
    private YhEcoCode yhEcoCode;

    @Id
    @Column(name = "eco_cd")
    private String ecoCode;

    @Id
    private String stdDt;

    @Id
    private String dayType;

    private String ecoVal;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String ecoCode;
        private String stdDt;
        private String dayType;
    }

}
