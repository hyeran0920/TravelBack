package filminkorea.fik.repositories;

import filminkorea.fik.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByRstrNmAndRstrRoadAddr(String rstrNm, String rstrRoadAddr);
}
