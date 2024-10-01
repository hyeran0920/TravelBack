package filminkorea.fik.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  // 프론트엔드 도메인 허용
public class GoogleApiController {

    // application.properties에서 API 키를 가져옴
    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    // Google Maps API 키를 반환하는 엔드포인트
    @GetMapping("/api/google-maps-api-key")
    public String getGoogleMapsApiKey() {
        System.out.println("Google Maps API Key: " + googleMapsApiKey);  // 로그 출력
        return googleMapsApiKey;
    }
}
