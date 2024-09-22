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
    private String addr;
}
