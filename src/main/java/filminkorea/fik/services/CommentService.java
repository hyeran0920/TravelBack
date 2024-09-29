package filminkorea.fik.services;

import filminkorea.fik.dtos.CommentDto;
import filminkorea.fik.entities.Comment;
import filminkorea.fik.entities.Contents;
import filminkorea.fik.repositories.CommentRepository;
import filminkorea.fik.repositories.ContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(CommentDto commentDto) {
        // Comment 객체를 contentsId 없이 생성
        Comment comment = new Comment(
                commentDto.getName(),
                commentDto.getContent(),
                commentDto.getCreatedAt() != null ? commentDto.getCreatedAt() : LocalDateTime.now()  // createdAt이 없으면 현재 시간으로 설정
        );

        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
