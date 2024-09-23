package demo.template.sb3_3template.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends TimeBaseEntity {

    @CreatedBy
    @Column(name = "register_id", updatable = false, length = 30)
    protected String registerId;

    @LastModifiedBy
    @Column(name = "modify_id", length = 30)
    protected String modifyId;

    public BaseEntity(LocalDateTime registerDt, LocalDateTime modifyDt, String registerId, String modifyId) {
        super(registerDt, modifyDt);
        this.registerId = registerId;
        this.modifyId = modifyId;
    }

}
