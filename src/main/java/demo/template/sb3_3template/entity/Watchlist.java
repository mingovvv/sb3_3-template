package demo.template.sb3_3template.entity;

import demo.template.sb3_3template.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Entity
@DynamicUpdate
@Table(name = "qb_watchlist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Watchlist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watchlist_id")
    private Long watchlistId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "type_cd")
    private String typeCode;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_nm")
    private String itemName;

    @Column(name = "std_date")
    private String standardDate;

    @Column(name = "position")
    private Integer position;

    @Builder
    public Watchlist(LocalDateTime registerDt, LocalDateTime modifyDt, String registerId, String modifyId, Long watchlistId, String userId, String typeCode, String itemId, String itemName, String standardDate, Integer position) {
        super(registerDt, modifyDt, registerId, modifyId);
        this.watchlistId = watchlistId;
        this.userId = userId;
        this.typeCode = typeCode;
        this.itemId = itemId;
        this.itemName = itemName;
        this.standardDate = standardDate;
        this.position = position;
    }

}
