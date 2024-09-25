package demo.template.sb3_3template.repository.custom;

public interface CustomWatchlistRepository {

    void updateWatchlistPosition(Long watchlistId, String userId, String standardDate, int previousPosition, int targetPosition);

}
