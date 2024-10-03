package filminkorea.fik.repositories;

import filminkorea.fik.entities.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FestivalRepository extends JpaRepository<Festival, String> {
    // 현재 진행 중인 축제 데이터 가져오기
    @Query("SELECT f FROM Festival f WHERE f.festival_begin_date <= :currentDate AND f.festival_end_date >= :currentDate")
    List<Festival> findCurrentFestivals(@Param("currentDate") LocalDate currentDate);

    // 사계절 별 축제 데이터 가져오기
    @Query("SELECT f FROM Festival f WHERE f.festival_season = :season")
    List<Festival> findFestivalsBySeason(@Param("season") String season);


}
