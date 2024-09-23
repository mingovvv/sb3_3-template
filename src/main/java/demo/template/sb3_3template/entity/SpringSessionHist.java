package demo.template.sb3_3template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "QB_SPRING_SESSION_HIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public SpringSessionHist(String sessionId, Long creationTime, Long lastAccessTime, Integer maxInactiveInterval, Long expiryTime) {
        this.sessionId = sessionId;
        this.creationTime = creationTime;
        this.lastAccessTime = lastAccessTime;
        this.maxInactiveInterval = maxInactiveInterval;
        this.expiryTime = expiryTime;
    }

    public static SpringSessionHist of(HttpSession httpSession) {
        return SpringSessionHist.builder()
                .sessionId(httpSession.getId())
                .creationTime(httpSession.getCreationTime())
                .lastAccessTime(httpSession.getLastAccessedTime())
                .maxInactiveInterval(httpSession.getMaxInactiveInterval())
                .expiryTime(httpSession.getLastAccessedTime() + httpSession.getMaxInactiveInterval() * 1000L)
                .build();
    }

    public void updateLastAccessedTime(HttpSession httpSession) {
        this.lastAccessTime = httpSession.getLastAccessedTime();
        this.expiryTime = httpSession.getLastAccessedTime() + httpSession.getMaxInactiveInterval() * 1000L;
    }

}
