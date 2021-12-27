package com.techforum.servicerating.repo;

import com.techforum.servicerating.repo.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {
    List<Rating> getRatingsByPostId(Long postId);
}