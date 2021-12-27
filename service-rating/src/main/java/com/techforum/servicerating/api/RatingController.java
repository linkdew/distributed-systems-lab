package com.techforum.servicerating.api;

import com.techforum.servicerating.api.dto.RatingDto;
import com.techforum.servicerating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<Double> getPostAverage(@PathVariable Long postId) {
        final Double average = ratingService.getAverageRate(postId);

        return ResponseEntity.ok(average);
    }

    @PostMapping
    public ResponseEntity<Long> createRating(@RequestBody RatingDto ratingDto) {
        final Long id = ratingService.createRating(ratingDto);

        return ResponseEntity.ok(id);
    }


}
