package filminkorea.fik.services;

import filminkorea.fik.dtos.ContentsDto;
import filminkorea.fik.entities.Contents;
import filminkorea.fik.repositories.ContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentsService {

    @Autowired
    private ContentsRepository contentsRepository;

    //entity -> dto 변환 메서드
    private ContentsDto convertToDto(Contents contents) {
        return new ContentsDto(
                contents.getCount_Num(),
                contents.getMedia_type(),
                contents.getTitle_NM(),
                contents.getPlace_Name(),
                contents.getRELATE_PLACE_DC(),
                contents.getOPER_TIME(),
                contents.getREST_TIME(),
                contents.getRSTDE_GUID_CN(),
                contents.getAddr(),
                contents.getLC_LA(),
                contents.getLC_LO(),
                contents.getTEL_NO()
        );
    }

    // contents 엔티티를 전부 조회하고 dto로 변환해서 반환하는 메서드
    // .map(this::converToDto) : 엔티티를 dto로 변환
    public List<ContentsDto> getAllContents() {
        List<Contents> contents = contentsRepository.findAll();
        return contents.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    //특정 id에 해당하는 contents 엔티티를 조회하고 dto로 변환하는 메서드
    //특정 id에 해당하는 db 결과가 있을 수도, 없을 수도 있어서 optional을 사용함.
    public Optional<ContentsDto> getContentsById(int count_num) {
        Optional<Contents> contents = contentsRepository.findById(count_num);
        //optional에서 엔티티를 dto로 변환
        return contents.map(this::convertToDto);
    }

    public List<String> getAllMovieContents() {
        return contentsRepository.findDistinctMovieContents();
    }

    public List<String> getAllDramaContents() {
        return contentsRepository.findDistinctDramaContents();
    }

    public List<ContentsDto> getLocationsAndAddressesByTitle(String contentTitle) {
        List<Object[]> results = contentsRepository.findLocationsAndAddressesByTitle(contentTitle);
        // Object[] 배열을 ContentsDto로 변환
        return results.stream()
                .map(result -> new ContentsDto((String) result[0], (String) result[1]))
                .collect(Collectors.toList());
    }

    // 작품 타이틀과 장소 이름을 기준으로 모든 데이터를 가져오는 서비스 메서드
    public ContentsDto findInformationByTitleAndPlace(String contentTitle, String placeName) {
        Contents result = contentsRepository.findInformationByTitleAndPlace(contentTitle, placeName);
        // 조회된 값이 없을 때 null 반환
        if (result == null) {
            return null;  // 예외 처리를 추가해도 좋습니다.
        }

        // 단일 객체를 DTO로 변환하여 반환
        return convertToDto(result);
    }


}
