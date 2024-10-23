package demo.template.sb3_3template.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsDto {

    private String newsId;
    private String vendor;
    private String newsDt;
    private String title;
    private String text;
    private String url;
    private String docId;
    private String sectorNm;
    private Double stmtScore;

    // Constructor
    public NewsDto(String newsId, String vendor, String newsDt, String title, String text, String url, String docId, String sectorNm, Double stmtScore) {
        this.newsId = newsId;
        this.vendor = vendor;
        this.newsDt = newsDt;
        this.title = title;
        this.text = text;
        this.url = url;
        this.docId = docId;
        this.sectorNm = sectorNm;
        this.stmtScore = stmtScore;
    }

    // Getters and setters
}
