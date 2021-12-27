package com.techforum.serviceposts.repo.model;

import com.techforum.serviceposts.api.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullPost {
    private Post post;
    private Double rating;
    private List<CommentDto> comments;
}
