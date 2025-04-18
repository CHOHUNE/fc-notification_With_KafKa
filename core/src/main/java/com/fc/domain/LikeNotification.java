package com.fc.domain;


import java.time.Instant;
import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;



@TypeAlias("LikeNotification")
@Getter
// mongoDB로 저장 후 java 객체로 역직렬화시 어떤 클래스를 사용할 건지 명시적으로 지정해주는 것
public class LikeNotification extends Notification{

    private final Long postId;
    private final List<Long> userList;


    public LikeNotification(String id, Long userId, NotificationType type, Instant createdAt,
        Instant occurredAt, Instant lastUpdatedAt, Instant deletedAt, Long postId,
        List<Long> userList) {
        super(id, userId, type, createdAt, occurredAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.userList = userList;
    }

    public void addLiker(Long userId, Instant occurredAt, Instant now, Instant retention) {
        this.userList.add(userId);
        this.setOccurredAt(occurredAt);
        this.setLastUpdatedAt(now);
        this.setDeletedAt(retention);
    }

    // domain 주도 설계 방식으로 -> DDD

    public void removerLiker(Long userId, Instant now ) {

        this.userList.remove(userId);
        this.setLastUpdatedAt(now);
        // 삭제 예정 업데이트, 좋아요를 취소했다고 언제 Occured 됐는지 필요 없음 -> update 시 최상단으로 오기 때문

    }



}
