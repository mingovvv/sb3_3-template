package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.EventOfSectorDto;

import java.util.List;

public interface CustomInfostockSectorEventRepository {

    List<EventOfSectorDto> findEventOfSector(List<String> sectors);

}
