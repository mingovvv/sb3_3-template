package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRate;
import demo.template.sb3_3template.enums.BsnsDays;
import demo.template.sb3_3template.enums.RankType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static demo.template.sb3_3template.entity.mart.infostock.QInfostockSectorIndex.infostockSectorIndex;
import static demo.template.sb3_3template.entity.mart.infostock.QInfostockSectorReturnRate.infostockSectorReturnRate;

public class CustomInfostockSectorReturnRateRepositoryImpl implements  CustomInfostockSectorReturnRateRepository {

    private final JPAQueryFactory queryFactory;

    public CustomInfostockSectorReturnRateRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Map<RankType, List<InfostockSectorReturnRate>> findByStdDtAndBsnsDays(String stdDt, BsnsDays bsnsDays) {

        Map<RankType, List<InfostockSectorReturnRate>> sectorReturnMap = new HashMap<>();

        List<InfostockSectorReturnRate> top5 = queryFactory
                .select(infostockSectorReturnRate)
                .from(infostockSectorReturnRate)
                .leftJoin(infostockSectorReturnRate.infostockSectorIndex, infostockSectorIndex).fetchJoin()
                .where(infostockSectorReturnRate.stdDt.eq(stdDt)
                        .and(infostockSectorReturnRate.bsnsDays.eq(bsnsDays.getBsnsDays())))
                .orderBy(infostockSectorReturnRate.returnRate.desc())
                .limit(5)
                .fetch();

        List<InfostockSectorReturnRate> bottom5 = queryFactory
                .select(infostockSectorReturnRate)
                .from(infostockSectorReturnRate)
                .leftJoin(infostockSectorReturnRate.infostockSectorIndex, infostockSectorIndex).fetchJoin()
                .where(infostockSectorReturnRate.stdDt.eq(stdDt)
                        .and(infostockSectorReturnRate.bsnsDays.eq(bsnsDays.getBsnsDays())))
                .orderBy(infostockSectorReturnRate.returnRate.asc())
                .limit(5)
                .fetch();

        sectorReturnMap.put(RankType.TOP, top5);
        sectorReturnMap.put(RankType.BOTTOM, bottom5);

        return sectorReturnMap;

    }

}
