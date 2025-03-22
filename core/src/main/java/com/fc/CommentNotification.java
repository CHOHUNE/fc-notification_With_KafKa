package com.fc;

import com.fc.config.NotificationType;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

@Getter
@TypeAlias("CommentNotification")
public class CommentNotification extends Notification {

    private final Long postId;
    private final Long writerId;
    private final String comment;

    public CommentNotification(String id, Long userId, NotificationType type,
        Instant createdAt, Instant occurredAt, Instant lastUpdatedAt,
        Instant deletedAt, Long postId, Long writerId, String comment) {
        super(id, userId, type, createdAt, occurredAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.writerId = writerId;
        this.comment = comment;
    }



}
