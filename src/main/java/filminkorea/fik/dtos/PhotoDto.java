package filminkorea.fik.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PhotoDto {

    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;

    public PhotoDto(Long id, String fileName, String filePath, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }
}
