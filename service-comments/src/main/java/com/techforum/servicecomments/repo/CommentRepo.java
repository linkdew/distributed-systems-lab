package com.techforum.servicecomments.repo;

import com.techforum.servicecomments.repo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
        List<Comment> findByPostId(Long id);

        List<Comment> findByPostIdIn(List<Long> ids);
}