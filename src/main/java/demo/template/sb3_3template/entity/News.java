package demo.template.sb3_3template.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@IdClass(News.CompositeKey.class)
@Entity
@Table(name = "news")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {

    @Id
    @Column(name = "news_id")
    private String newsId;

    @Id
    @Column(name = "vendor")
    private String vendor;

    @Column(name = "news_dt")
    private String newsDt;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "url")
    private String url;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "sector_nm")
    private String sectorNm;

    @Column(name = "stmt_score")
    private Double stmtScore;

    @NoArgsConstructor
    @EqualsAndHashCode
    static public class CompositeKey implements Serializable {
        private String newsId;
        private String vendor;
    }

}

