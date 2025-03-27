package com.fc.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {

    private Long id;
    private String content;
    private Instant createdAt;

}
