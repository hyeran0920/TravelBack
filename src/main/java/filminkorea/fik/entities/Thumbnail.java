package filminkorea.fik.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "thumbnail")
@Getter
@Setter
@NoArgsConstructor
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String media_type;
    private String title_nm;
    private String image_Url;
    private String image_Name;

}
