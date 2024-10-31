package demo.template.sb3_3template.entity;

import demo.template.common.converter.BooleanToYNAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "cq_channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CHANNEL_ID", length = 50, nullable = false)
    private String channelId;

    @Column(name = "SERVICE_NM", length = 50, nullable = false)
    private String serviceNm;

    @Column(name = "CLIENT_NM", length = 30, nullable = false)
    private String clientNm;

    @Column(name = "CHANNEL_KEY", length = 200)
    private String channelKey;

    @Column(name = "CHANNEL_SECRET", length = 200)
    private String channelSecret;

    @Column(name = "DEPARTMENT", length = 20)
    private String department;

    @Column(name = "MANAGER", length = 20)
    private String manager;

    @Column(name = "ACTIVATE_DT")
    private LocalDateTime activateDt;

    @Column(name = "DEACTIVATE_DT")
    private LocalDateTime deactivateDt;

    @Convert(converter = BooleanToYNAttributeConverter.class)
    @Column(name = "AUTH_YN", length = 1)
    private Boolean authYn;

    @Convert(converter = BooleanToYNAttributeConverter.class)
    @Column(name = "TTS_YN", length = 1)
    private Boolean ttsYn;

    @Convert(converter = BooleanToYNAttributeConverter.class)
    @Column(name = "COMMENT_YN", length = 1)
    private Boolean commentYn;

    @Convert(converter = BooleanToYNAttributeConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn;

    @Column(name = "CREATED_ID", length = 50, nullable = false)
    private String createdId;

    @Column(name = "CREATED_DT", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "UPDATED_ID", length = 50)
    private String updatedId;

    @Column(name = "UPDATED_DT")
    private LocalDateTime updatedDt;

    @Column(name = "insert_date", nullable = false)
    private LocalDateTime insertDate;

}
