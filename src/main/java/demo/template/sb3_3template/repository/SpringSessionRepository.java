package demo.template.sb3_3template.repository;

import demo.template.sb3_3template.entity.SpringSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringSessionRepository extends JpaRepository<SpringSession, String> {

    Optional<SpringSession> findBySessionId(String id);

}
