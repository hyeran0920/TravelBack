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

        ContentsDto contentsDto = new ContentsDto();

        contentsDto.setCount_Num(contents.getCount_Num());
        contentsDto.setMedia_type(contents.getMedia_type());
        contentsDto.setTitle_NM(contents.getTitle_NM());
        contentsDto.setPlace_Name(contents.getPlace_Name());
        contentsDto.setAddr(contents.getAddr());

        return contentsDto;
        
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

    //이 방식은 dto가 아닌 entity를 사용해서 하기 때문에 보안 상의 문제가 생길 수 있음.
//    getAllContents() : 데이터베이스에서 모든 contents 엔티티를 조회해서 반환한다.
//    public List<Contents> getAllContents() {
//        return contentsRepository.findAll();
//    }
//
//    findByID() : 특정 id에 해당하는 정보 추출
//    public Optional<Contents> getContentById(int count_num) {
//

}
