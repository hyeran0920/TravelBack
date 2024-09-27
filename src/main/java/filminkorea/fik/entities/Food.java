package filminkorea.fik.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    private String rstrNm;  // 식당명
    private String rstrClNm;  // 분류명
    private String rstrRoadAddr;  // 도로명 주소
    private String rstrLnbrAddr;  // 지번 주소
    private double rstrLatPos;  // 위도
    private double rstrLotPos;  // 경도
}
