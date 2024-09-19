package demo.template.sb3_3template.repository;

import demo.template.sb3_3template.entity.SpringSessionHist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringSessionHistRepository extends JpaRepository<SpringSessionHist, String> {
}
