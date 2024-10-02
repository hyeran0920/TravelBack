package filminkorea.fik.services;

import filminkorea.fik.dtos.ContentsDto;
import filminkorea.fik.dtos.FoodDto;
import filminkorea.fik.dtos.ResponseWrapper;
import filminkorea.fik.entities.FoodMap;
import filminkorea.fik.repositories.FoodMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodMapService {

    private final FoodMapRepository foodMapRepository;

    @Autowired
    public FoodMapService(FoodMapRepository foodMapRepository) {
        this.foodMapRepository = foodMapRepository;
    }

    // 여러 지역과 분류 데이터를 추출하여 저장하는 메서드
    @Transactional
    public void fetchAndSaveFoodMapData() {
        // 데이터베이스에 이미 저장된 데이터가 있는지 확인
        long existingDataCount = foodMapRepository.count();  // 저장된 데이터의 개수 확인

        if (existingDataCount > 0) {
            // 이미 데이터가 존재하면 다운로드 및 저장을 중단
            System.out.println("데이터가 이미 존재합니다. 추가 다운로드를 중단합니다.");
            return;  // 데이터 저장 과정을 중단
        }

        String[] areas = {"서울", "인천", "강원", "부산", "경주", "제주", "전라남도","충청남도","충청북도","전라북도","경기","경상남도","경상북도"}; // 여러 지역 설정
        String[] classifications = {"한식", "서양식","퓨전"}; // 분류 설정

        for (String area : areas) {
            for (String classification : classifications) {
                // 이미 데이터베이스에 저장된 데이터가 있는지 확인
                boolean exists = foodMapRepository.existsByFoodMapPlaceNameAndFoodMapRoadAddr(classification, area);

                if (!exists) {
                    // API URL을 동적으로 생성
                    String baseUrl = String.format("http://api.kcisa.kr/openapi/API_CNV_063/request?serviceKey=369e440d-6970-4db4-8594-638767251bb3&areaNm=%s&clNm=%s", area, classification);

                    // API로부터 데이터를 받아와 저장하는 메서드 호출
                    saveDataFromApi(baseUrl);
                } else {
                    System.out.println(area + " - " + classification + " 데이터가 이미 존재합니다. 저장하지 않습니다.");
                }
            }
        }
    }

    // API로부터 데이터를 받아와 FoodMap 엔티티로 변환하여 저장하는 메서드
    private void saveDataFromApi(String baseUrl) {
        try {
            // API 호출
            RestTemplate restTemplate = new RestTemplate();
            String xmlResponse = restTemplate.getForObject(baseUrl, String.class);

            // XML 응답을 파싱하여 객체로 변환
            JAXBContext jaxbContext = JAXBContext.newInstance(ResponseWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ResponseWrapper responseWrapper = (ResponseWrapper) unmarshaller.unmarshal(new StringReader(xmlResponse));

            List<FoodDto> foodDtoList = responseWrapper.getItems();

            // 데이터가 비어있으면 처리 중단
            if (foodDtoList == null || foodDtoList.isEmpty()) {
                System.out.println("API에서 데이터를 가져오지 못했습니다.");
                return;
            }

            // 데이터 저장 (최대 5개)
            int limit = 5;
            for (int i = 0; i < Math.min(foodDtoList.size(), limit); i++) {
                FoodDto dto = foodDtoList.get(i);

                // 중복 여부 확인 후 저장
                Optional<FoodMap> existingFoodMap = foodMapRepository.findByFoodMapPlaceNameAndFoodMapRoadAddr(dto.getRstrNm(), dto.getRstrRoadAddr());

                if (!existingFoodMap.isPresent()) {
                    // FoodDto를 FoodMap으로 변환하여 데이터 저장
                    FoodMap foodMap = new FoodMap();
                    foodMap.setFoodMapPlaceName(dto.getRstrNm());
                    foodMap.setFoodMapsClName(dto.getRstrClNm());
                    foodMap.setFoodMapRoadAddr(dto.getRstrRoadAddr());
                    foodMap.setFoodMapAddr(dto.getRstrLnbrAddr());
                    foodMap.setFoodMapLatPos(dto.getRstrLatPos());
                    foodMap.setFoodMapLotPos(dto.getRstrLotPos());

                    // 데이터베이스에 저장
                    foodMapRepository.save(foodMap);
                }
            }

            System.out.println("데이터가 성공적으로 저장되었습니다.");

        } catch (Exception e) {
            System.out.println("데이터 저장 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    // 전체 FoodMap 데이터를 가져오는 메서드
    public List<FoodMap> getAllFoodPlacesOnTheMap() {
        return foodMapRepository.findTotalFoodPlaceOnTheMap();
    }

}
