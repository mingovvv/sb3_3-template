package demo.template.sb3_3template.repository.mart;

import demo.template.common.annotation.TrackQueries;
import demo.template.sb3_3template.dto.NewsDto;
import demo.template.sb3_3template.dto.NewsProjection;
import demo.template.sb3_3template.entity.News;
import demo.template.sb3_3template.entity.mart.YhStockCode;
import demo.template.sb3_3template.repository.custom.CustomYhStockCodeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface YhStockCodeRepository extends JpaRepository<YhStockCode, YhStockCode.CompositeKey>, CustomYhStockCodeRepository {

    Optional<YhStockCode> findByStockCd(String stockCode);

//    @Query("SELECT y FROM YhStockCode y LEFT JOIN FETCH y.yhStockReturnRates yr WHERE y.stockNameKr = :stockName AND yr.bsnsDays = :bsnsDays AND yr.stdDt = :stdDt")
//    Optional<YhStockCode> findStockReturnRate(@Param(value = "stockName") String stockName, @Param(value = "bsnsDays") String bsnsDays, @Param(value = "stdDt") String stdDt);

    @TrackQueries
    @Query(value = """
    SELECT ranked.news_id as newsId, 
           ranked.vendor as vendor, 
           ranked.news_dt as newsDt, 
           ranked.title as title, 
           ranked.text as text, 
           ranked.url as url, 
           ranked.doc_id as docId, 
           ranked.sector_nm as sectorNm, 
           ranked.stmt_score as stmtScore
    FROM (
             SELECT
                 news_id,
                 vendor,
                 news_dt,
                 title,
                 text,
                 url,
                 doc_id,
                 sector_nm,
                 stmt_score,
                 ROW_NUMBER() OVER (PARTITION BY LEFT(news_dt, 6) ORDER BY stmt_score DESC, news_dt ASC) as rn
             FROM cde_news
         ) AS ranked
    WHERE rn = 1
    ORDER BY news_dt;
    """, nativeQuery = true)
    List<NewsProjection> getTopNewsNative();
}
