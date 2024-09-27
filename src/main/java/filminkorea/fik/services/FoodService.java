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

    @Transactional
    public void fetchAndSaveXmlData() {
        String baseUrl = "http://api.kcisa.kr/openapi/API_CNV_063/request?serviceKey=369e440d-6970-4db4-8594-638767251bb3&areaNm=서울&clNm=한식";

        try {
            // RestTemplate을 통해 XML 데이터를 받아옴
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(baseUrl, String.class);

            // XML 데이터를 출력하여 확인
//            System.out.println("API 응답: " + xmlResponse);

            // JAXBContext를 이용해 XML 데이터를 파싱하여 ResponseWrapper 객체로 변환
            JAXBContext jaxbContext = JAXBContext.newInstance(ResponseWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // XML 응답을 ResponseWrapper 객체로 변환
            ResponseWrapper responseWrapper = (ResponseWrapper) unmarshaller.unmarshal(new StringReader(xmlResponse));

            // items 리스트를 추출
            List<FoodDto> foodDtoList = responseWrapper.getItems();
            System.out.println(xmlResponse + "Raw XML Response: ");
            System.out.println("Items node: " + responseWrapper.getItems());



            // Null 체크 추가
            if (foodDtoList == null || foodDtoList.isEmpty()) {
                System.out.println("데이터가 없습니다.");
                return;  // 리스트가 비어있으면 메서드 종료
            }

            // 데이터 저장
            for (FoodDto dto : foodDtoList) {
                Optional<Food> existingFood = foodRepository.findByRstrNmAndRstrRoadAddr(dto.getRstrNm(), dto.getRstrRoadAddr());

                // 동일한 데이터가 없을 경우에만 저장
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