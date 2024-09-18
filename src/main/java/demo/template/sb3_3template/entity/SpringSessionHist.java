package demo.template.sb3_3template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "SPRING_SESSION_HIST")
public class SpringSessionHist {

    @Id
    @Column(name = "SESSION_ID", columnDefinition = "CHAR(36)")
    private String sessionId;

    @Column(name = "CREATION_TIME")
    private Long creationTime;

    @Column(name = "LAST_ACCESS_TIME")
    private Long lastAccessTime;

    @Column(name = "MAX_INACTIVE_INTERVAL")
    private Integer maxInactiveInterval;

    @Column(name = "EXPIRY_TIME")
    private Long expiryTime;

}
