package filminkorea.fik.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Layer {

    @Id
    @Column(name = "count_Num")
    private int countNum;

    @Column(name = "title_nm")
    private String title_nm;

    @Column(name = "place_name")
    private String place_name;

    @Column(name = "imgsrc")
    private String imgsrc;  // 이미지 경로 필드

    // 추가된 필드의 getter와 setter는 Lombok이 자동으로 생성
}


