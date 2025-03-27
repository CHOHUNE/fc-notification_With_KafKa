package com.fc;


import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationGetService {

    @Autowired
    private NotificationRepository repository;

    public Optional<Notification> getNotificationByTypeCommentId(NotificationType type , Long commentId) {

        return repository.findByTypeAndCommentId(type, commentId);
    }

    public Optional<Notification> getNotificationByTypePostId(NotificationType type, Long postId) {
        return repository.findByTypeAndPostId(type, postId);
    }
    public Optional<Notification> getNotificationByTypeAndFollowerId(NotificationType type,Long userId, Long followerId) {
        return repository.findByTypeAndUserIdAndFollowerId(type, userId, followerId);
    }



}
