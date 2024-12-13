package demo.template.sb3_3template.service;

import demo.template.sb3_3template.dto.res.FinancialDictionaryDto;
import demo.template.sb3_3template.dto.res.PaginationRes;
import demo.template.sb3_3template.entity.RecommendQuestion;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.repository.RecommendQuestionRepository;
import demo.template.sb3_3template.repository.WatchlistRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private static final int RECOMMENDATION_LIMIT = 5;

    private final WatchlistRepository watchlistRepository;
    private final RecommendQuestionRepository recommendQuestionRepository;

    public HomeService(WatchlistRepository watchlistRepository, RecommendQuestionRepository recommendQuestionRepository) {
        this.watchlistRepository = watchlistRepository;
        this.recommendQuestionRepository = recommendQuestionRepository;
    }

    public PaginationRes<FinancialDictionaryDto> getFinancialDictionary(int page, int size) {
        return PaginationRes.toRes(watchlistRepository.findByCondition(page, size));
    }

    public PaginationRes<FinancialDictionaryDto> getSearchFinancialDictionary(String word) {
//        List<Watchlist> searchFinancialDictionary = watchlistRepository.findSearchFinancialDictionary(word);
        return null;
    }

    public List<String> getRecom(String userId) {

        List<String> recommendations;

        if (ObjectUtils.isEmpty(userId)) {
            List<RecommendQuestion> commonQuestions = recommendQuestionRepository.findByCodeAndUseYnTrue("RANDOM");
            Collections.shuffle(commonQuestions);
            recommendations = commonQuestions.stream().map(RecommendQuestion::getQuestion).limit(RECOMMENDATION_LIMIT).toList();
        } else {

            List<Watchlist> watchlist = null;

            if (watchlist.isEmpty()) {
                List<RecommendQuestion> commonQuestions = recommendQuestionRepository.findByCodeAndUseYnTrue("RANDOM");

                // shuffle
                Collections.shuffle(commonQuestions);
                recommendations = commonQuestions.stream().map(RecommendQuestion::getQuestion).limit(RECOMMENDATION_LIMIT).toList();
            } else {

                List<RecommendQuestion> questions = recommendQuestionRepository.findByUseYnTrue();
                Map<Boolean, List<RecommendQuestion>> partitionedQuestions = questions.stream().collect(Collectors.partitioningBy(s -> s.getCode().equals("RANDOM")));

                List<RecommendQuestion> commonQuestions = partitionedQuestions.get(Boolean.TRUE);
                List<RecommendQuestion> userSpecificQuestions = partitionedQuestions.get(Boolean.FALSE);

                // shuffle
                Collections.shuffle(watchlist);
                Collections.shuffle(commonQuestions);
                Collections.shuffle(userSpecificQuestions);

                recommendations = watchlist.stream()
                        .flatMap(item -> userSpecificQuestions.stream()
                                .filter(question -> item.getTypeCode().equalsIgnoreCase(question.getKeyType()))
                                .map(question -> question.getQuestion().replace("{key}", item.getItemName())))
                        .limit(RECOMMENDATION_LIMIT)
                        .collect(Collectors.toList());

                if (recommendations.size() < RECOMMENDATION_LIMIT) {
                    recommendations.addAll(commonQuestions.stream().map(RecommendQuestion::getQuestion).limit(RECOMMENDATION_LIMIT - recommendations.size()).toList());
                }

            }
        }
        return recommendations;
    }

}
