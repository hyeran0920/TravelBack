package filminkorea.fik.dtos;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "item")
@NoArgsConstructor
public class FoodMapDto {
    @XmlElement(name = "rstrNm")
    private String foodMapPlaceName;  // 식당명

    @XmlElement(name = "rstrClNm")
    private String foodMapsClName;  // 분류명

    @XmlElement(name = "rstrRoadAddr")
    private String foodMapRoadAddr;  // 도로명 주소

    @XmlElement(name = "rstrLnbrAddr")
    private String foodMapAddr;  // 지번 주소

    @XmlElement(name = "rstrLatPos")
    private double foodMapLatPos;  // 위도

    @XmlElement(name = "rstrLotPos")
    private double foodMapLotPos;  // 경도
}
