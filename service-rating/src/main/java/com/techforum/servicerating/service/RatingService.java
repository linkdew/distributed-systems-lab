package com.techforum.servicerating.service;

import com.techforum.servicerating.api.dto.RatingDto;
import com.techforum.servicerating.repo.RatingRepo;
import com.techforum.servicerating.repo.model.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepo;

    public Double getAverageRate(Long postId) {
        List<Rating> ratingList = ratingRepo.getRatingsByPostId(postId);

        return ratingList.stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(Double.NaN);
    }

    public Long createRating(RatingDto ratingDto) {
        final Long postId = ratingDto.postId();
        final Long userId = ratingDto.userId();
        Double postRating = ratingDto.postRating();
        if (postRating > 10){
            postRating = 10.0;
        } else  if (postRating < 0){
            postRating = 0.0;
        }

        final Rating rating = Rating.builder()
                .postId(postId)
                .userId(userId)
                .rating(postRating)
                .build();
        final Rating newRating = ratingRepo.save(rating);

        return newRating.getId();
    }
}
