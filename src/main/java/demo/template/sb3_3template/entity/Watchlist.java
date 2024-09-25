package demo.template.sb3_3template.entity;

import demo.template.common.utils.DateUtil;
import demo.template.sb3_3template.dto.req.WatchlistReq;
import demo.template.sb3_3template.entity.common.BaseEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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

    public static Watchlist toEntity(WatchlistReq.PostWatch postWatch, int maxPosition) {
        return Watchlist.builder()
                .userId(postWatch.userId())
                .typeCode(postWatch.marketCode())
                .itemId(postWatch.itemId())
                .itemName(null) // todo ...
                .standardDate(DateUtil.getMinusDay(0))
                .position(maxPosition + 1)
                .build();
    }

    public void updateStandardDate(WatchlistReq.PatchWatch patchWatch) {

        if (StringUtils.isNotEmpty(patchWatch.standardDate())) {
            this.standardDate = patchWatch.standardDate();
        }

        if (ObjectUtils.isNotEmpty(patchWatch.position())) {
            this.position = patchWatch.position();
        }

    }

}
