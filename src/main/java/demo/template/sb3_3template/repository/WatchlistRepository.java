package demo.template.sb3_3template.repository;

import demo.template.common.annotation.TrackQueries;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.repository.custom.CustomWatchlistRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long>, CustomWatchlistRepository {

    @TrackQueries
    List<Watchlist> findByUserIdIn(List<String> userId);

    Optional<Watchlist> findByUserIdAndTypeCodeAndItemId(String userId, String marketCode, String itemId);

    Optional<Watchlist> findByWatchlistIdAndUserId(Long watchlistId, String userId);

    @Query("SELECT MAX(w.position) FROM Watchlist w WHERE w.userId = :userId")
    int getMaxPosition(@Param("userId") String userId);

    @Query("UPDATE Watchlist w SET w.position = w.position + 1 WHERE w.userId = :userId AND w.position < :targetPosition")
    int updateUpperPositionByUserId(@Param("userId") String userId, @Param("targetPosition") int targetPosition);

//    @Query("SELECT f FROM F f where f.word like %:word%")
//    List<Watchlist> findSearchFinancialDictionary(@Param("query") String word);

}
