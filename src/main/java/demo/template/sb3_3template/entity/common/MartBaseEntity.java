package demo.template.sb3_3template.entity.common;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public abstract class MartBaseEntity {

    @Column(name = "insert_date")
    protected LocalDateTime registerId;

}