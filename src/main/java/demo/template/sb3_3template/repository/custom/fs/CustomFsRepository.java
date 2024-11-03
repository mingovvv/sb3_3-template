package demo.template.sb3_3template.repository.custom.fs;

import demo.template.sb3_3template.entity.fs.Fs;

import java.util.List;

public interface CustomFsRepository {

    List<Fs> findFs(String minusYear, String endYearOfPeriod);

}
