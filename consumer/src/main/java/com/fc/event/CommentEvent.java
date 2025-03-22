package com.fc.event;

import lombok.Data;
import org.yaml.snakeyaml.comments.CommentType;

@Data
public class CommentEvent {

    private CommentEventType type;
    private Long postId;
    private Long userId;
    private Long commentId;

}
