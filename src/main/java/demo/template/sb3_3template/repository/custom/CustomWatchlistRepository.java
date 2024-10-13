package demo.template.sb3_3template.repository.custom;

import demo.template.sb3_3template.controller.TestController;
import demo.template.sb3_3template.dto.res.FinancialDictionaryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomWatchlistRepository {

    void updateWatchlistPosition(Long watchlistId, String userId, String standardDate, int previousPosition, int targetPosition);

    Page<FinancialDictionaryDto> findByCondition(int page, int size);

    void updateTest(String userId, List<TestController.TestDto.TTT> ttt1);
}
