package filminkorea.fik;

import filminkorea.fik.services.FoodService;
import filminkorea.fik.services.FoodMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FoodService foodService;
    private final FoodMapService foodMapService;  // 추가된 FoodMapService

    @Autowired
    public DataInitializer(FoodService foodService, FoodMapService foodMapService) {
        this.foodService = foodService;
        this.foodMapService = foodMapService;  // 주입된 FoodMapService
    }

    @Override
    public void run(String... args) throws Exception {
        // 프로그램 시작 시 모든 데이터를 다운로드하여 저장
        System.out.println("데이터 검색 및 저장 시작...");

        // FoodService를 사용하여 데이터를 다운로드 및 저장
        foodService.fetchAndSaveXmlData();  // 필터 없이 모든 데이터를 다운로드 및 저장

        // FoodMapService를 사용하여 데이터를 다운로드 및 저장
        foodMapService.fetchAndSaveFoodMapData();  // FoodMap 데이터 다운로드 및 저장

        System.out.println("데이터 다운로드 및 저장 완료");
    }
}
