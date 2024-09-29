package filminkorea.fik.repositories;

import filminkorea.fik.entities.Comment;
import filminkorea.fik.entities.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
