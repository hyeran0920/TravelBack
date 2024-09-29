package filminkorea.fik.controllers;

import filminkorea.fik.dtos.CommentDto;
import filminkorea.fik.entities.Comment;
import filminkorea.fik.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) {
        Comment createdComment = commentService.createComment(commentDto);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
}
