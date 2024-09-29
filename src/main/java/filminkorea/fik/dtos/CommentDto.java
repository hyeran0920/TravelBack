package filminkorea.fik.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private String name;
    private String content;private LocalDateTime createdAt;  // createdAt 필드 추가

    public CommentDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public CommentDto(String name, String content, LocalDateTime createdAt) {
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;
    }
}
