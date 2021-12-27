package com.techforum.servicecomments.service;


import com.techforum.servicecomments.repo.CommentRepo;
import com.techforum.servicecomments.repo.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class CommentService {
    public final CommentRepo commentRepo;

    public List<Comment> fetchAllByPostId(Long id){
        return commentRepo.findByPostId(id);
    }

    public List<Comment> fetchAllByPostIds(List<Long> ids){
        return commentRepo.findByPostIdIn(ids);
    }

    public Long create(Long postId, Long userId, String commentText, Date creationDate){
        final Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .commentText(commentText)
                .creationDate(creationDate)
                .build();
        final Comment savedComment = commentRepo.save(comment);

        return savedComment.getId();
    }

    public void delete(Long id){
        commentRepo.deleteById(id);
    }

    public Comment getById(Long id) {
        final Optional<Comment> comment = commentRepo.findById(id);
        return comment.get();
    }
}
