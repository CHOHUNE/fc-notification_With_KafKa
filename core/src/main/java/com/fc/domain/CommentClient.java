package com.fc.domain;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CommentClient {

    private final Map<Long, Comment> commentList = new HashMap<>();

    public CommentClient() {
        commentList.put(1L, new Comment(1L,"content1", Instant.now()));
        commentList.put(2L, new Comment(2L,"content2", Instant.now()));
        commentList.put(3L, new Comment(3L,"content3", Instant.now()));
    }

    public Comment getComment(Long id) {
        return commentList.get(id);
    }
}
