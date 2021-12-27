package com.techforum.serviceposts.service;

import com.techforum.serviceposts.api.dto.CommentDto;
import com.techforum.serviceposts.repo.model.FullPost;
import com.techforum.serviceposts.repo.PostRepo;
import com.techforum.serviceposts.repo.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@Service
public final class PostService {
    public final PostRepo postRepo;
    private final String commentUrlAddress = "http://service-comments:8080/comments";
    private final String ratingUrlAddress = "http://service-rating:8082/rating";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Post> fetchAll() throws IllegalArgumentException {
        return postRepo.findAll();
    }

    public FullPost fetchFullPostById(Long id) {
        final Post post = postRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found!"));
        final Long postId = post.getId();

        final String ratingLocation = ratingUrlAddress + String.format("/post/%d", postId);
        final Double rating = getRating(ratingLocation);

        final String commentLocation = commentUrlAddress + String.format("/post/%d", postId);
        final List<CommentDto> comments = getCommentDtos(commentLocation);

        return FullPost.builder()
                .post(post)
                .rating(rating)
                .comments(comments)
                .build();
    }

    public Post fetchById(Long id) {
        final Optional<Post> maybePost = postRepo.findById(id);

        if (maybePost.isEmpty()) throw new IllegalArgumentException("post not found!");
        else return maybePost.get();
    }

    public long create(long user_id, String post_name, String post_text, Date creation_date, String status) {
        final Post post = Post.builder()
                .user_id(user_id)
                .post_name(post_name)
                .post_text(post_text)
                .creation_date(creation_date)
                .status(status)
                .build();
        final Post savedPost = postRepo.save(post);

        return savedPost.getId();
    }

    public void update(long id, String post_name, String post_text, Date creation_date, String status) throws IllegalArgumentException {
        final Optional<Post> maybePost = postRepo.findById(id);
        if (maybePost.isEmpty()) throw new IllegalArgumentException("PostDTO not found!");

        final Post post = maybePost.get();
        if (post_name != null && !post_name.isBlank()) post.setPost_name(post_name);
        if (post_text != null && !post_name.isBlank()) post.setPost_text(post_text);
        if (creation_date != null && !post_name.isBlank()) post.setCreation_date(creation_date);
        if (status != null && !post_name.isBlank()) post.setStatus(status);
        postRepo.save(post);
    }

    public void delete(long id) {
        postRepo.deleteById(id);
    }

    private List<CommentDto> getCommentDtos(String addressLocation) {
        final ResponseEntity<CommentDto[]> commentDtoResponseEntity = restTemplate.getForEntity(addressLocation, CommentDto[].class);
        final CommentDto[] commentDtoArr = commentDtoResponseEntity.getBody();

        final List<CommentDto> comments;

        if (commentDtoArr != null)
            comments = Arrays.asList(commentDtoArr);
        else
            comments = Collections.emptyList();

        return comments;
    }

    private Double getRating(String location) {
        final ResponseEntity<Double> commentDtoResponseEntity = restTemplate.getForEntity(location, Double.class);

        return commentDtoResponseEntity.getBody();
    }
}
