package filminkorea.fik.services;

import filminkorea.fik.entities.Festival;
import filminkorea.fik.repositories.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FestivalService {
    private final FestivalRepository festivalRepository;

    @Autowired
    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public List<Festival> getCurrentFestivals() {
        LocalDate currentDate = LocalDate.now();
        return festivalRepository.findCurrentFestivals(currentDate); // LocalDate 그대로 전달
    }

    public List<Festival> getFestivalsBySeason(String season) {
        return festivalRepository.findFestivalsBySeason(season);
    }
}
