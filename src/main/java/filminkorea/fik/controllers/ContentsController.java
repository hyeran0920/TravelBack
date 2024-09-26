package filminkorea.fik.controllers;

import filminkorea.fik.dtos.ContentsDto;
import filminkorea.fik.services.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ContentsController {

    @Autowired
    private ContentsService contentsService;

    //모든 contents dto를 조회하는 메서드
    @GetMapping("/content/all")
    public List<ContentsDto> getAllContents() {
        return contentsService.getAllContents();
    }

    // contents에서 영화 타이틀 가져오기
    @GetMapping("/content/getAllMovies")
    public List<String> getAllMovieContents() {
        return contentsService.getAllMovieContents();
    }

    // contents에서 드라마 타이틀 가져오기
    @GetMapping("/content/getAllDramas")
    public List<String> getAllDramaContents() {
        return contentsService.getAllDramaContents();
    }

    @GetMapping("/content/getLocationsAndAddressesByTitle")
    public List<ContentsDto> getLocationsAndAddressesByTitle(@RequestParam String title){
        return contentsService.getLocationsAndAddressesByTitle(title);
    }

    // 작품 타이틀과 장소 이름을 기준으로 정보를 조회하는 엔드포인트
    @GetMapping("/content/getInformationByTitleAndPlace")
    public ResponseEntity<ContentsDto> getInformationByTitleAndPlace(
            @RequestParam("title") String title,
            @RequestParam("place") String place) {
        ContentsDto result = contentsService.findInformationByTitleAndPlace(title, place);

        // 결과가 없을 때 404 반환
        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);  // 결과가 있으면 200 OK로 반환
    }

    // 주소를 기준으로 콘텐츠를 조회하는 엔드포인트
    @GetMapping("/content/searchByAddress")
    public List<ContentsDto> getContentsByAddress(@RequestParam(defaultValue = "") String address) {
        return contentsService.getContentsByAddress(address);
    }
}
