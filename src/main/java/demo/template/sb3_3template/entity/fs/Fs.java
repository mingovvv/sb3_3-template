package demo.template.sb3_3template.entity.fs;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@IdClass(Fs.CompositeKey.class)
@Entity
@Table(name = "fs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fs {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "acct_std", referencedColumnName = "acct_std"),
            @JoinColumn(name = "fs_type", referencedColumnName = "fs_type"),
            @JoinColumn(name = "acct_cd", referencedColumnName = "acct_cd"),
            @JoinColumn(name = "industry", referencedColumnName = "industry")
    })
    private FsCode fsCode;

    @Id
    @Column(name = "acct_std", nullable = false, length = 1)
    private String acctStd;

    @Id
    @Column(name = "fs_type", nullable = false, length = 1)
    private String fsType;

    @Id
    @Column(name = "acct_cd", nullable = false, length = 10)
    private String acctCd;

    @Id
    @Column(name = "industry", nullable = false, length = 64)
    private String industry;

    @Id
    @Column(name = "cum_type", nullable = false, length = 1)
    private String cumType;

    @Id
    @Column(name = "stock_cd", nullable = false, length = 6)
    private String stockCd;

    @Id
    @Column(name = "fs_dt", nullable = false, length = 6)
    private String fsDt;

    @Id
    @Column(name = "quarter", nullable = false, length = 1)
    private String quarter;

    @Column(name = "data", nullable = false, precision = 28, scale = 5)
    private BigDecimal data;

    @Column(name = "yoy_per")
    private Double yoyPer;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String acctStd;
        private String fsType;
        private String acctCd;
        private String industry;
        private String cumType;
        private String stockCd;
        private String fsDt;
        private String quarter;
    }

}
