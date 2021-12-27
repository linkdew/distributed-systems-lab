package com.techforum.servicecomments.api;

import com.techforum.servicecomments.api.dto.CommentDto;
import com.techforum.servicecomments.repo.model.Comment;
import com.techforum.servicecomments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/post/{post_id}")
    public ResponseEntity<List<Comment>> getAllByPostId(@PathVariable Long post_id) {
        final List<Comment> comments = commentService.fetchAllByPostId(post_id);

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Comment>> getAllByPostIds(@RequestBody List<Long> post_ids) {
        final List<Comment> comments = commentService.fetchAllByPostIds(post_ids);

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
        final Comment comment = commentService.getById(id);

        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CommentDto commentDto) {
        final Long postId = commentDto.postId();
        final Long userId = commentDto.userId();
        final String commentText = commentDto.commentText();
        final Date creationDate = commentDto.creationDate();

        final Long id = commentService.create(postId, userId, commentText, creationDate);

        final String location = String.format("/comments/%d", id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}