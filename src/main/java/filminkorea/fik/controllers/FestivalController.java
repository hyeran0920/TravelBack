package filminkorea.fik.controllers;

import filminkorea.fik.entities.Festival;
import filminkorea.fik.repositories.FestivalRepository;
import filminkorea.fik.services.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/festival")
@CrossOrigin(origins = "http://localhost:3000")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    @GetMapping("/current")
    public List<Festival> getCurrentFestivals() {
        return festivalService.getCurrentFestivals();
    }

    // 사계절 별 축제 데이터 가져오기
    @GetMapping("/season")
    public List<Festival> getFestivalsBySeason(@RequestParam String season) {
        return festivalService.getFestivalsBySeason(season);
    }
}
