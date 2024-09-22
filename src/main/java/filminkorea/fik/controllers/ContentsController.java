package filminkorea.fik.controllers;

import filminkorea.fik.dtos.ContentsDto;
import filminkorea.fik.services.ContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
}
