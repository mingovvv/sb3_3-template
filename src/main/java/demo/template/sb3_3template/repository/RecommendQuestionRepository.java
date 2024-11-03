package demo.template.sb3_3template.repository;

import demo.template.sb3_3template.entity.RecommendQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendQuestionRepository extends JpaRepository<RecommendQuestion, Long> {

    @Query(value = "SELECT * FROM cde_cq_recommend_question q WHERE q.KEY_TYPE IS NULL ORDER BY rand() LIMIT :limit", nativeQuery = true)
    List<RecommendQuestion> findRandom(@Param("limit") int limit);

    List<RecommendQuestion> findByCodeAndUseYnTrue(String code);

    List<RecommendQuestion> findByUseYnTrue();

}
