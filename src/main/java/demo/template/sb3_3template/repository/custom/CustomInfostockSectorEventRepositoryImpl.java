package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.EventOfSectorDto;
import demo.template.sb3_3template.dto.QEventOfSectorDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QInfostockSectorEvent.infostockSectorEvent;

@Repository
public class CustomInfostockSectorEventRepositoryImpl implements CustomInfostockSectorEventRepository {

    private final JPAQueryFactory queryFactory;

    public CustomInfostockSectorEventRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<EventOfSectorDto> findEventOfSector(List<String> sectorCodeList) {

        return queryFactory
                .select(new QEventOfSectorDto(
                        infostockSectorEvent.themeCd,
                        infostockSectorEvent.event
                        )
                )
                .from(infostockSectorEvent)
                .where(
                        infostockSectorEvent.themeCd.in(sectorCodeList)
                                .and(infostockSectorEvent.histDt.eq(
                                        queryFactory
                                                .select(infostockSectorEvent.histDt.max())
                                                .from(infostockSectorEvent)
                                                .where(infostockSectorEvent.themeCd.in(sectorCodeList))
                                                .groupBy(infostockSectorEvent.themeCd)
                                ))
                ).fetch();

    }

}
