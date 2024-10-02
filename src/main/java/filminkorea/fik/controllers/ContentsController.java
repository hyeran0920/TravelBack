package filminkorea.fik.controllers;

import filminkorea.fik.dtos.ContentsDto;
import filminkorea.fik.entities.Contents;
import filminkorea.fik.services.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

    // repository에서 검색 결과를 받아 비즈니스 로직 실행
//    @GetMapping("/content/searchList")
//    public List<Contents> searchList(@RequestParam String title) {
//        System.out.println("Received title: " + title); // title 값 확인
//        return contentsService.search(title);
//    }

    // 제목, 장소명, 주소로 검색하는 엔드포인트
    // Repository & Service에서 SearchTerm을 쓰는 이유 : 메서드 내부에서 검색어를 처리할 때 사용하는 변수
    // controller에서 query를 쓰는 이유 : 클라이언트가 요청할 때 전달하는 URL 파라미터의 이름
    // 일치시켜도 상관은 없음!
    @GetMapping("/content/searchList")
    public List<ContentsDto> searchContents(@RequestParam("query") String query) {
        // 요청 파라미터 "query"로 검색어를 받음
        // 서비스 계층의 searchByTitlePlaceOrAddress 메서드를 호출하여 검색 결과를 반환
        return contentsService.searchByTitlePlaceOrAddress(query);
    }

    // 랜덤한 7개의 촬영지 정보를 가져오는 엔드포인트
    @GetMapping("/content/random")
    public List<ContentsDto> getRandomContents() {
        return contentsService.getRandomContents();
    }

    @GetMapping("/content/map")
    public List<ContentsDto> findTotalContentPlaceOnTheMap(){
        return contentsService.findTotalContentPlaceOnTheMap();
    }
}
