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
    @Query("SELECT c.place_Name, c.addr, c.LC_LA, c.LC_LO FROM Contents c WHERE c.title_NM = LOWER(:title)")
    List<Object[]> findLocationsAndAddressesByTitle(@Param("title") String title);

    // 제목과 촬영지를 기준으로 해당 장소의 상세 정보를 가져오는 쿼리
    @Query("SELECT c FROM Contents c WHERE c.title_NM = LOWER(:title) AND c.place_Name = LOWER(:place)")
    Contents findInformationByTitleAndPlace(@Param("title") String title, @Param("place") String place);

    // 주소를 기준으로 콘텐츠 목록을 가져오는 쿼리
    @Query("SELECT c FROM Contents c WHERE c.addr LIKE %:address%")
    List<Contents> findContentsByAddress(@Param("address") String address);

    // 제목을 기준으로 검색하는 쿼리 : containing을 붙여주면 like 검색이 가능
//    @Query("SELECT c FROM Contents c WHERE LOWER(c.title_NM) LIKE LOWER(CONCAT('%', :title_NM, '%'))")
//    List<Contents> findByTitle_NMContaining(@Param("title_NM") String title_NM);

    //제목, place_Name, addr 기준으로 검색 => 대소문자 구분x 근데 저희는 한국어라서...적용 안될지도?
    @Query("SELECT c FROM Contents c WHERE " +
            "LOWER(c.title_NM) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.place_Name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.addr) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    // 검색어를 파라미터로 받아서 제목, 장소명, 주소 중 하나라도 일치하는 콘텐츠 목록을 반환
    List<Contents> searchByTitleOrPlaceOrAddress(@Param("searchTerm") String searchTerm);



}
