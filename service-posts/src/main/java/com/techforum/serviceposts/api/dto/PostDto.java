package com.techforum.serviceposts.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PostDto {
    private long user_id;
    private String post_name;
    private String post_text;
    private Date creation_date;
    private String status;
}
