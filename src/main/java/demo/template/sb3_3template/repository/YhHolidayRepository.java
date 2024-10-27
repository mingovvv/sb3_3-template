package demo.template.sb3_3template.repository;

import demo.template.sb3_3template.entity.raw.YhHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YhHolidayRepository extends JpaRepository<YhHoliday, YhHoliday.CompositeKey> {
}
