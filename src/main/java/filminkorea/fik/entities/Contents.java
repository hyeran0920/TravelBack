package filminkorea.fik.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//본인이 기존에 작성해뒀던 name을 쓰면 될 것 같습니다.
@Table(name = "contents")
@Getter
@Setter
@NoArgsConstructor
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Count_Num;
    private String media_type;
    private String title_NM;
    private String place_Name;
    private String addr;
}
