package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.dto.res.FinancialDictionaryDto;
import org.springframework.data.domain.Page;

public interface CustomWatchlistRepository {

    void updateWatchlistPosition(Long watchlistId, String userId, String standardDate, int previousPosition, int targetPosition);

    Page<FinancialDictionaryDto> findByCondition(int page, int size);

}
