package filminkorea.fik.controllers;

import filminkorea.fik.entities.Food;
import filminkorea.fik.entities.FoodMap;
import filminkorea.fik.services.FoodMapService;
import filminkorea.fik.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;
    private final FoodMapService foodMapService;

    // 생성자에서 두 서비스 의존성을 주입받음
    @Autowired
    public FoodController(FoodService foodService, FoodMapService foodMapService) {
        this.foodService = foodService;
        this.foodMapService = foodMapService;
    }

    // /food/all 경로로 모든 Food 데이터를 가져오는 API
    @GetMapping("/all")
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

//    // API 호출하여 데이터 저장
//    @GetMapping("/map")
//    public String fetchAndSaveFoodMapData() {
//        foodMapService.fetchAndSaveFoodMapData();
//        return "Food map data fetched and saved!";
//    }

    // 전체 FoodMap 데이터를 클라이언트에게 반환하는 API
    @GetMapping("/map")
    public ResponseEntity<List<FoodMap>> getAllFoodPlaces() {
        List<FoodMap> foodPlaces = foodMapService.getAllFoodPlacesOnTheMap();
        return ResponseEntity.ok(foodPlaces);
    }

}
