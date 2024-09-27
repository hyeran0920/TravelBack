package filminkorea.fik;

import filminkorea.fik.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FoodService foodService;

    @Autowired
    public DataInitializer(FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 프로그램 시작 시 모든 데이터를 다운로드하여 저장
        System.out.println("데이터 검색 및 저장 시작...");

        // 모든 데이터를 가져오는 메서드 호출
        foodService.fetchAndSaveXmlData();  // 필터 없이 모든 데이터를 다운로드 및 저장

        System.out.println("데이터 다운로드 및 저장 완료");
    }
}
