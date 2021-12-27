package com.techforum.servicecomments.api.dto;

import java.util.Date;

public record CommentDto(Long postId, Long userId, String commentText, Date creationDate) {
}
