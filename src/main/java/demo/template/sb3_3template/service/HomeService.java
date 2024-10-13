package demo.template.sb3_3template.service;

import demo.template.sb3_3template.dto.res.FinancialDictionaryDto;
import demo.template.sb3_3template.dto.res.PaginationRes;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.repository.WatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final WatchlistRepository watchlistRepository;

    public HomeService(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    public PaginationRes<FinancialDictionaryDto> getFinancialDictionary(int page, int size) {
        return PaginationRes.toRes(watchlistRepository.findByCondition(page, size));
    }

    public PaginationRes<FinancialDictionaryDto> getSearchFinancialDictionary(String word) {
//        List<Watchlist> searchFinancialDictionary = watchlistRepository.findSearchFinancialDictionary(word);
        return null;
    }
}
