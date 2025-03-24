package com.fc;


import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@Document("notifications") // mongoDB 어떤 collection 에 넣을 것인지 결정
public abstract class Notification {

    private String id;
    private Long userId;
    private NotificationType type;
    private Instant createdAt;
    private Instant occurredAt; // 알림 event 가 실제 발생된 시간
    private Instant lastUpdatedAt;
    private Instant deletedAt;

}
