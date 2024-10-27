package demo.template.sb3_3template.repository.fs;

import demo.template.sb3_3template.entity.fs.FsCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FsCodeRepository extends JpaRepository<FsCode, FsCode.CompositeKey> {
}
