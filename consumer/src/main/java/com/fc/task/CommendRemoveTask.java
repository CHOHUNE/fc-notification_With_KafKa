package com.fc.task;

import com.fc.NotificationGetService;
import com.fc.NotificationRemoveService;
import com.fc.NotificationType;
import com.fc.Post;
import com.fc.PostClient;
import com.fc.event.CommentEvent;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommendRemoveTask {

    @Autowired
    PostClient postClient;

    @Autowired
    NotificationGetService getService;

    @Autowired
    NotificationRemoveService removeService;



    public void processEvent(CommentEvent event) {

        Post post = postClient.getPost(event.getPostId());
        if ((Objects.equals(post.getUserId(), event.getUserId()))) {
            return;
        }
        getService.getNotificationByTypeCommentId(NotificationType.COMMENT, event.getCommentId())
            .ifPresentOrElse(
                notification -> removeService.deleteById(notification.getId()),
                ()-> log.error("Notification not found"));
    }
}
