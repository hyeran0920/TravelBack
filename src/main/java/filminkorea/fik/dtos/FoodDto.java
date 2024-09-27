package filminkorea.fik.dtos;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@Getter
@XmlRootElement(name = "item")
public class FoodDto {

    @XmlElement(name = "rstrNm")
    private String rstrNm;

    @XmlElement(name = "rstrClNm")
    private String rstrClNm;

    @XmlElement(name = "rstrRoadAddr")
    private String rstrRoadAddr;

    @XmlElement(name = "rstrLnbrAddr")
    private String rstrLnbrAddr;

    @XmlElement(name = "rstrLatPos")
    private double rstrLatPos;

    @XmlElement(name = "rstrLotPos")
    private double rstrLotPos;

    // Getters and setters...
}

