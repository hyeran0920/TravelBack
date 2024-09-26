package filminkorea.fik.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LayerDto {

    private int countNum;  // 파일 번호
    private String title_nm;  // 타이틀 이름 (영화 또는 드라마 제목)
    private String place_name;  // 장소 이름
    private String imgsrc;  // 이미지 경로
}
