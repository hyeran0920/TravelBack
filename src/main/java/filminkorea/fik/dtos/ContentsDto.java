package filminkorea.fik.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ContentsDto {

    //컨트롤러와 서비스 간에 데이터를 전달할 때 사용
    private int Count_Num;
    private String media_type;
    private String title_NM;
    private String place_Name;
    private String RELATE_PLACE_DC;
    private String OPER_TIME;
    private String REST_TIME;
    private String RSTDE_GUID_CN;
    private String addr;
    private String LC_LA;
    private String LC_LO;
    private String TEL_NO;

    // place_Name과 addr을 받는 생성자 추가 : 작품의 장소와 주소를 알기 위해 사용될 예정
    public ContentsDto(String place_Name, String addr, String LC_LA, String LC_LO) {
        this.place_Name = place_Name;
        this.addr = addr;
        this.LC_LA = LC_LA;
        this.LC_LO = LC_LO;
    }

    // ContentsDto에 모든 필드를 받는 생성자 추가
    public ContentsDto(int countNum, String mediaType, String titleNm, String placeName, String relatePlaceDc, String operTime, String restTime, String rstdeGuidCn, String addr, String lcLa, String lcLo, String telNo) {
        this.Count_Num = countNum;
        this.media_type = mediaType;
        this.title_NM = titleNm;
        this.place_Name = placeName;
        this.RELATE_PLACE_DC = relatePlaceDc;
        this.OPER_TIME = operTime;
        this.REST_TIME = restTime;
        this.RSTDE_GUID_CN = rstdeGuidCn;
        this.addr = addr;
        this.LC_LA = lcLa;
        this.LC_LO = lcLo;
        this.TEL_NO = telNo;
    }
    public ContentsDto(String titleNm, String placeName, String addr, String lcLa, String lcLo) {
        this.title_NM = titleNm;
        this.place_Name = placeName;
        this.addr = addr;
        this.LC_LA = lcLa;
        this.LC_LO = lcLo;
    }


}
