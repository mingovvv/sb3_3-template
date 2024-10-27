package demo.template.sb3_3template.repository.custom.infostock;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.entity.mart.infostock.InfostockSectorReturnRateM;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.infostock.QInfostockSectorReturnRateM.infostockSectorReturnRateM;

public class CustomInfostockSectorReturnRateMRepositoryImpl implements CustomInfostockSectorReturnRateMRepository {

    private final JPAQueryFactory queryFactory;

    public CustomInfostockSectorReturnRateMRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<InfostockSectorReturnRateM> findByThemeNmAndThemeDtGoe(String sectorNm, String themeDt) {
        return queryFactory
                .select(infostockSectorReturnRateM)
                .from(infostockSectorReturnRateM)
                .where(infostockSectorReturnRateM.themeNm.eq(sectorNm)
                        .and(infostockSectorReturnRateM.themeDt.goe(themeDt)))
                .orderBy(infostockSectorReturnRateM.themeDt.asc())
                .fetch();
    }

}
