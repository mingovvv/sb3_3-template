package demo.template.sb3_3template;

import demo.template.sb3_3template.entity.raw.YhHoliday;
import demo.template.sb3_3template.repository.YhHolidayRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class HolidayInitializer {

    private final YhHolidayRepository yhHolidayRepository;

    private static final Map<String, List<String>> holidayMap = new HashMap<>();

    HolidayInitializer(YhHolidayRepository yhHolidayRepository) {
        this.yhHolidayRepository = yhHolidayRepository;
    }

    @PostConstruct
    public void init() {
        List<YhHoliday> holidays = yhHolidayRepository.findAll();
        for (YhHoliday holiday : holidays) {
            holidayMap.computeIfAbsent(holiday.getContryCd(), k -> new ArrayList<>()).add(holiday.getClsYM());
        }
    }

    public Map<String, List<String>> getHolidayMap() {
        return holidayMap;
    }

}
