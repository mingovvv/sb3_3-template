package demo.template.sb3_3template.repository.fs;

import demo.template.sb3_3template.entity.fs.Fs;
import demo.template.sb3_3template.repository.custom.fs.CustomFsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FsRepository extends JpaRepository<Fs, Fs.CompositeKey>, CustomFsRepository {
}
