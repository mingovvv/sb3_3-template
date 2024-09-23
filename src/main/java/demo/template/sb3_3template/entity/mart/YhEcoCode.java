package demo.template.sb3_3template.entity.mart;

import demo.template.sb3_3template.entity.common.MartBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "yh_eco_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YhEcoCode extends MartBaseEntity {

    @Id
    private String ecoCode;

    private String type;

    @Column(name = "eco_nm_kr")
    private String ecoNameKr;

    @Column(name = "eco_nm_en")
    private String ecoNameEn;

}
