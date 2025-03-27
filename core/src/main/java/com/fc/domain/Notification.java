package com.fc.domain;


import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@AllArgsConstructor
@Document("notifications") // mongoDB 어떤 collection 에 넣을 것인지 결정
public abstract class Notification {

    @Field(targetType = FieldType.STRING)
    private String id;
    // mongoDB 는 기본적으로 ID 필드를 objectID 타입으로 지정한다.
    // 고유한 식별자 타입으로 ObjectId('123') 과 같은 형태로 저장되는데
    // 가독성과 테스트 용이성 혹은 애플리케이션에서 사용하는 고유 비즈니스 키를 사용하고 싶을 때
    // 위와 같으 objectId 가 아닌 고유 타입으로 지정해준다

    private Long userId;
    private NotificationType type;
    private Instant createdAt;
    private Instant occurredAt; // 알림 event 가 실제 발생된 시간
    private Instant lastUpdatedAt;
    private Instant deletedAt;

}
