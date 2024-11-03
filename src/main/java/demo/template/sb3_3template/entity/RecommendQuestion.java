package demo.template.sb3_3template.entity;

import demo.template.common.converter.BooleanToYNAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "cq_recommend_question")
public class RecommendQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "KEY_TYPE")
    private String keyType;

    @Column(name = "QUESTION", nullable = false)
    private String question;

    @Column(name = "USE_YN", nullable = false, length = 1)
    @Convert(converter = BooleanToYNAttributeConverter.class)
    private Boolean useYn;

    @Column(name = "CREATED_ID", nullable = false)
    private String createdId;

    @Column(name = "CREATED_DT", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "UPDATED_ID")
    private String updatedId;

    @Column(name = "UPDATED_DT")
    private LocalDateTime updatedDt;
}
