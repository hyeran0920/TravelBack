package filminkorea.fik.services;

import filminkorea.fik.dtos.FoodDto;
import filminkorea.fik.dtos.ResponseWrapper;
import filminkorea.fik.entities.Food;
import filminkorea.fik.repositories.FoodRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    //10개만 일단 저장 + 데베가 있으면 또 작동 안하도록
    @Transactional
    public void fetchAndSaveXmlData() {
        // 먼저 데이터베이스에 데이터가 존재하는지 확인
        long existingDataCount = foodRepository.count();  // 데이터베이스에 저장된 데이터의 개수 확인

        if (existingDataCount > 0) {
            // 이미 데이터가 존재하면 다운로드 및 저장을 중지
            System.out.println("데이터가 이미 존재합니다. 추가 다운로드를 중단합니다.");
            return;  // 데이터 저장 과정을 중단
        }

        String baseUrl = "http://api.kcisa.kr/openapi/API_CNV_063/request?serviceKey=369e440d-6970-4db4-8594-638767251bb3&areaNm=서울&clNm=한식";

        try {
            // RestTemplate을 통해 XML 데이터를 받아옴
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(baseUrl, String.class);

            // JAXBContext를 이용해 XML 데이터를 파싱하여 ResponseWrapper 객체로 변환
            JAXBContext jaxbContext = JAXBContext.newInstance(ResponseWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ResponseWrapper responseWrapper = (ResponseWrapper) unmarshaller.unmarshal(new StringReader(xmlResponse));

            List<FoodDto> foodDtoList = responseWrapper.getItems();

            if (foodDtoList == null || foodDtoList.isEmpty()) {
                System.out.println("API로부터 데이터를 받아오지 못했습니다.");
                return;
            }

            // 데이터 저장 (최대 10개만 저장)
            int limit = 10;
            for (int i = 0; i < Math.min(foodDtoList.size(), limit); i++) {
                FoodDto dto = foodDtoList.get(i);
                Optional<Food> existingFood = foodRepository.findByRstrNmAndRstrRoadAddr(dto.getRstrNm(), dto.getRstrRoadAddr());

                if (!existingFood.isPresent()) {
                    Food food = new Food();
                    food.setRstrNm(dto.getRstrNm());
                    food.setRstrClNm(dto.getRstrClNm());
                    food.setRstrRoadAddr(dto.getRstrRoadAddr());
                    food.setRstrLnbrAddr(dto.getRstrLnbrAddr());
                    food.setRstrLatPos(dto.getRstrLatPos());
                    food.setRstrLotPos(dto.getRstrLotPos());

                    foodRepository.save(food);
                }
            }

            System.out.println("데이터가 성공적으로 저장되었습니다.");

        } catch (Exception e) {
            System.out.println("데이터 저장 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }



    // 모든 Food 데이터를 가져오는 메서드
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

}