package com.techforum.servicerating.api.dto;

public record RatingDto (Long postId, Long userId, Double postRating) {
}
