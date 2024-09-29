package filminkorea.fik.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 댓글 작성자 이름
    private String content;  // 댓글 내용
    private LocalDateTime createdAt;

    // 기본 생성자 (기존)
    public Comment(String name, String content) {
        this.name = name;
        this.content = content;
        this.createdAt = LocalDateTime.now();  // 기본적으로 현재 시간으로 설정
    }

    // 새로운 생성자 추가
    public Comment(String name, String content, LocalDateTime createdAt) {
        this.name = name;
        this.content = content;
        this.createdAt = createdAt;  // 외부에서 생성된 createdAt을 직접 설정 가능
    }
}
