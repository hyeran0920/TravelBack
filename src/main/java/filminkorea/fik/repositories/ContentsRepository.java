package filminkorea.fik.repositories;

import filminkorea.fik.entities.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Integer> {
    // JPQL을 사용하여 DISTINCT한 영화 제목들을 가져오는 쿼리
    @Query("SELECT DISTINCT c.title_NM FROM Contents c WHERE c.media_type = 'movie'")
    List<String> findDistinctMovieContents();

    // JPQL을 사용하여 DISTINCT한 드라마 제목들을 가져오는 쿼리
    @Query("SELECT DISTINCT c.title_NM FROM Contents c WHERE c.media_type = 'drama'")
    List<String> findDistinctDramaContents();

    // 제목을 기준으로 촬영지와 주소 리스트를 가져오는 쿼리
    @Query("SELECT c.place_Name, c.addr FROM Contents c WHERE c.title_NM = LOWER(:title)")
    List<Object[]> findLocationsAndAddressesByTitle(@Param("title") String title);

    // 제목과 촬영지를 기준으로 해당 장소의 상세 정보를 가져오는 쿼리
    @Query("SELECT c FROM Contents c WHERE c.title_NM = LOWER(:title) AND c.place_Name = LOWER(:place)")
    Contents findInformationByTitleAndPlace(@Param("title") String title, @Param("place") String place);

    // 주소를 기준으로 콘텐츠 목록을 가져오는 쿼리
    @Query("SELECT c FROM Contents c WHERE c.addr LIKE %:address%")
    List<Contents> findContentsByAddress(@Param("address") String address);

}
