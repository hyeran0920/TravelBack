package filminkorea.fik.controllers;

import filminkorea.fik.dtos.ContentsDto;
import filminkorea.fik.services.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
