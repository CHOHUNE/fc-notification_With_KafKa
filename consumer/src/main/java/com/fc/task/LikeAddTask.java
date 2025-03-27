package com.fc.task;

import com.fc.domain.LikeNotification;
import com.fc.domain.Notification;
import com.fc.service.NotificationGetService;
import com.fc.utils.NotificationIdGenerator;
import com.fc.service.NotificationSaveService;
import com.fc.domain.NotificationType;
import com.fc.domain.Post;
import com.fc.client.PostClient;
import com.fc.event.LikeEvent;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LikeAddTask {


    @Autowired
    private PostClient postClient;
    @Autowired
    private NotificationGetService notificationGetService;
    @Autowired
    private NotificationSaveService notificationSaveService;

    public void processEvent(LikeEvent event) {

        Post post = postClient.getPost(event.getPostId());

        if (post == null) {
            log.error("Post is null with postID:{}", event.getPostId());
            return;
        }

        if (post.getUserId().equals(event.getPostId())) {
            return;
        }
        //notification 1. 신규 생성 2. 업데이트
        Notification notification = createOrUpdateNotification(event, post);

        // likeNotification db 저장
        notificationSaveService.upsert(notification); //insert X upsert O
    }

    private Notification createOrUpdateNotification(LikeEvent event, Post post) {

        Optional<Notification> optionalNotification = notificationGetService.getNotificationByTypePostId(
            NotificationType.LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = Instant.now().plus(90,ChronoUnit.DAYS);

        if (optionalNotification.isPresent()) {
            // 업데이트
            log.info("업데이트");
         return updateNotification((LikeNotification) optionalNotification.get(), event, now, retention);

        } else {
            //신규 생성
            log.info("신규생성");
            return createNotification(post, event,now,retention);
        }

    }

    private Notification updateNotification(LikeNotification notification,LikeEvent event, Instant now, Instant retention) {

         notification.addLiker(
            event.getUserId(),
            event.getCreatedAt(),
            now,
            retention
        );
        return notification;
    }

    private Notification createNotification (Post post,LikeEvent event,Instant now,Instant retention) {



        return new LikeNotification(
            NotificationIdGenerator.generateId(),
            post.getUserId(),
            NotificationType.LIKE,
            event.getCreatedAt(),
            now,
            now,
            retention,
            post.getId(),
            List.of(event.getUserId()) // 새로 생성은 LikeEvent를 발생시킨 당사자 한 명이라 List.of 로
        );
    }

}
