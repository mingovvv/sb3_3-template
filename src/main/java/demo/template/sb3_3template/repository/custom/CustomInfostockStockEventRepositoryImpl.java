package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QStockWithEventDto;
import demo.template.sb3_3template.dto.StockWithEventDto;
import demo.template.sb3_3template.entity.mart.InfostockStockEvent;
import demo.template.sb3_3template.entity.mart.QInfostockStockEvent;
import demo.template.sb3_3template.repository.mart.InfostockStockEventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static demo.template.sb3_3template.entity.mart.QInfostockStockEvent.infostockStockEvent;

@Repository
public class CustomInfostockStockEventRepositoryImpl implements CustomInfostockStockEventRepository {

    private final JPAQueryFactory queryFactory;

    public CustomInfostockStockEventRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<StockWithEventDto> findStockWithEvent(List<String> stockNameList) {

        return queryFactory
                .select(new QStockWithEventDto(
                            infostockStockEvent.stockCd,
                            infostockStockEvent.histDt
                    )
                )
                .from(infostockStockEvent)
                .where(
                    infostockStockEvent.stockCd.in(stockNameList)
                            .and(infostockStockEvent.histDt.eq(
                                    queryFactory
                                            .select(infostockStockEvent.histDt.max())
                                            .from(infostockStockEvent)
                                            .where(infostockStockEvent.stockCd.in(stockNameList))
                                            .groupBy(infostockStockEvent.stockCd)
                            ))
                ).fetch();
    }

}
