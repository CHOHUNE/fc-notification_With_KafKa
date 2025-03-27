package com.fc.domain;

import java.time.Instant;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

@Getter
@TypeAlias("FollowerNotification") // mongoDB document 에 명시적으로 type 을 설정함 설정을 안할시 클래스 이름이 들어감.
public class FollowNotification extends Notification {

    private final long followerId;



    public FollowNotification(String id, Long userId, NotificationType type,
        Instant createdAt, Instant occurredAt, Instant lastUpdatedAt,
        Instant deletedAt, long followerId) {
        super(id, userId, type, createdAt, occurredAt, lastUpdatedAt, deletedAt);
        this.followerId = followerId;
    }

}
