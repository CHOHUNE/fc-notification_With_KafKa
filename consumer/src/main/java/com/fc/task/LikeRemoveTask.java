package com.fc.task;

import com.fc.LikeNotification;
import com.fc.Notification;
import com.fc.NotificationGetService;
import com.fc.NotificationRemoveService;
import com.fc.NotificationSaveService;
import com.fc.NotificationType;
import com.fc.event.LikeEvent;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikeRemoveTask {


    private final NotificationGetService notificationGetService;
    private final NotificationRemoveService notificationRemoveService;
    private final NotificationSaveService notificationSaveService;


    public void processEvent(LikeEvent event) {
        //내가 빠지면 좋아요가 0인 경우 -> Like 삭제
        //내가 빠지면 좋아요가 1 이상인 경우 -> userList 에서 나만 삭제

        Optional<Notification> notificationByTypePostId = notificationGetService.getNotificationByTypePostId(
            NotificationType.LIKE,
            event.getPostId());

        if (notificationByTypePostId.isEmpty()) {
            log.error("Notification NOT exist with this postId : {}", event.getPostId());
            return;
        }

        LikeNotification likeNotification = (LikeNotification) notificationByTypePostId.get();
        removerLikerAndUpdateNotification(likeNotification, event);
        // private 처리해서 capsule 화

    }

    private void removerLikerAndUpdateNotification (LikeNotification likeNotification, LikeEvent event){

        // 비지 않은 경우 ( 저장은 하지 않은 상태 )

        likeNotification.removerLiker(event.getUserId(),Instant.now());


        // 여기서 저장할지 or 삭제할지 결정

        // 삭제를 했는데 -> likers가 빈 경우 -> 삭제
        if (likeNotification.getUserList().isEmpty()) {
            notificationRemoveService.deleteById(likeNotification.getId());
        }else{
            // 삭제를 했는데 -> likers가 비지 않은 경우 -> 업데이트
            notificationSaveService.upsert(likeNotification);
        }
    }
}
