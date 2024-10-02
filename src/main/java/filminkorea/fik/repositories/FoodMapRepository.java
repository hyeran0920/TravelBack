package filminkorea.fik.repositories;

import filminkorea.fik.entities.FoodMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodMapRepository extends JpaRepository<FoodMap, Long> {
    Optional<FoodMap> findByFoodMapPlaceNameAndFoodMapRoadAddr(String foodMapPlaceName, String foodMapRoadAddr);
    boolean existsByFoodMapPlaceNameAndFoodMapRoadAddr(String foodMapPlaceName, String foodMapRoadAddr);

    @Query("SELECT f FROM FoodMap f")
    List<FoodMap> findTotalFoodPlaceOnTheMap();
}
