package demo.template.sb3_3template.repository;

import demo.template.sb3_3template.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, News.CompositeKey> {
}
