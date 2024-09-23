package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QSectorWithStockDto;
import demo.template.sb3_3template.dto.QStockCompositeDto;
import demo.template.sb3_3template.dto.StockCompositeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QInfostockTheme.infostockTheme;
import static demo.template.sb3_3template.entity.mart.QInfostockThemeStock.infostockThemeStock;
import static demo.template.sb3_3template.entity.mart.QYhStockCode.yhStockCode;

@Repository
public class CustomInfostockThemeRepositoryImpl implements CustomYhStockCodeRepository {

    private final JPAQueryFactory queryFactory;

    public CustomInfostockThemeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<StockCompositeDto> findAllStockWithIndexAndSector() {
        return null;
    }

}
