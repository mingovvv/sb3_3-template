package demo.template.sb3_3template.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.template.sb3_3template.dto.QEventOfStockDto;
import demo.template.sb3_3template.dto.EventOfStockDto;
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
    public List<EventOfStockDto> findEventOfStock(List<String> stockCodeList) {

        return queryFactory
                .select(new QEventOfStockDto(
                                infostockStockEvent.stockCd,
                                infostockStockEvent.histDt
                        )
                )
                .from(infostockStockEvent)
                .where(
                        infostockStockEvent.stockCd.in(stockCodeList)
                                .and(infostockStockEvent.histDt.eq(
                                        queryFactory
                                                .select(infostockStockEvent.histDt.max())
                                                .from(infostockStockEvent)
                                                .where(infostockStockEvent.stockCd.in(stockCodeList))
                                                .groupBy(infostockStockEvent.stockCd)
                                ))
                ).fetch();

    }

}
