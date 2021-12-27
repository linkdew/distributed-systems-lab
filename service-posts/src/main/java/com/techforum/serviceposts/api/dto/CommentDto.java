package com.techforum.serviceposts.api.dto;

import java.util.Date;

public record CommentDto(Long id,
                         Long postId,
                         Long userId,
                         String commentText,
                         Date creationDate) {
}
