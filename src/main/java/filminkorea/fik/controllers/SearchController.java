package filminkorea.fik.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Value("${google.api.key}")
    private String apiKey; // 구글 API 키를 application.properties에서 가져옴

    // Google Places API로 장소 검색 및 별점 추가
    @GetMapping("/place-photo")
    public ResponseEntity<String> getPlacePhoto(@RequestParam String query) {
        // URL 직접 생성
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" +
                "input=" + query + "&" +
                "inputtype=textquery&" +
                "fields=photos,formatted_address,name,rating,place_id&" +
                "key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        try {
            // Google API에 요청
            String result = restTemplate.getForObject(url, String.class);

            // 응답 결과를 그대로 반환 (여기에는 rating 정보가 포함됨)
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("Error fetching search results: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching search results: " + e.getMessage());
        }
    }
}