package filminkorea.fik.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ThumbnailDto {

    private int id;
    private String media_type;
    private String title_nm;
    private String image_Url;
    private String image_Name;
}
