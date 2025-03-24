package com.fc;


import java.time.Instant;
import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("LikeNotification")
@Getter
// mongoDB로 저장 후 java 객체로 역직렬화시 어떤 클래스를 사용할 건지 명시적으로 지정해주는 것
public class LikeNotification extends Notification{

    private final Long postId;
    private final List<Long> likerIds;


    public LikeNotification(String id, Long userId, NotificationType type, Instant createdAt,
        Instant occurredAt, Instant lastUpdatedAt, Instant deletedAt, Long postId,
        List<Long> userList) {
        super(id, userId, type, createdAt, occurredAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.likerIds = userList;
    }
}
