package demo.template.sb3_3template.entity.fs;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(FsCode.CompositeKey.class)
@Entity
@Table(name = "fs_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FsCode {

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

    @Column(name = "fs_nm", nullable = false, length = 128)
    private String fsNm;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String acctStd;
        private String fsType;
        private String acctCd;
        private String industry;
    }

}
