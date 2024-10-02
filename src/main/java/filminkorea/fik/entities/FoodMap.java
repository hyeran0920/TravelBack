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
public class FoodMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodMapId; // 기본 키
    private String foodMapPlaceName;  // 식당명
    private String foodMapsClName;  // 분류명
    private String foodMapRoadAddr;  // 도로명 주소
    private String foodMapAddr;  // 지번 주소
    private double foodMapLatPos;  // 위도
    private double foodMapLotPos;  // 경도
}
