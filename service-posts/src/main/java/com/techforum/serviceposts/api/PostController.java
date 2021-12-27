package com.techforum.serviceposts.api;

import com.techforum.serviceposts.api.dto.PostDto;
import com.techforum.serviceposts.repo.model.FullPost;
import com.techforum.serviceposts.repo.model.Post;
import com.techforum.serviceposts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<com.techforum.serviceposts.repo.model.Post>> index() {
        final List<com.techforum.serviceposts.repo.model.Post> posts = postService.fetchAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<FullPost> showFull(@PathVariable Long id) {
        try {
            final FullPost fullPost = postService.fetchFullPostById(id);
            return ResponseEntity.ok(fullPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> show(@PathVariable Long id) {
        try {
            final Post post = postService.fetchById(id);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PostDto postDTO) {
        final long user_id = postDTO.getUser_id();
        final String post_name = postDTO.getPost_name();
        final String post_text = postDTO.getPost_text();
        final Date creation_date = postDTO.getCreation_date();
        final String status = postDTO.getStatus();

        final long id = postService.create(user_id, post_name, post_text, creation_date, status);
        final String location = String.format("/posts/%d", id);

        return ResponseEntity.created(URI.create(location)).build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody PostDto postDTO) {
        final String post_name = postDTO.getPost_name();
        final String post_text = postDTO.getPost_text();
        final Date creation_date = postDTO.getCreation_date();
        final String status = postDTO.getStatus();

        try {
            postService.update(id, post_name, post_text, creation_date, status);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
