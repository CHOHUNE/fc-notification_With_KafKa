package com.fc.task;


import com.fc.domain.Comment;
import com.fc.domain.CommentClient;
import com.fc.domain.CommentNotification;
import com.fc.domain.Notification;
import com.fc.utils.NotificationIdGenerator;
import com.fc.service.NotificationSaveService;
import com.fc.domain.NotificationType;
import com.fc.domain.Post;
import com.fc.client.PostClient;
import com.fc.event.CommentEvent;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentAddTask {

    @Autowired
    PostClient postClient;

    @Autowired
    CommentClient commentClient;

    @Autowired
    NotificationSaveService saveService;

    public void processEvent(CommentEvent event) {

        // 내가 생성한 댓글일 경우 return
        Post post = postClient.getPost(event.getPostId());
        if (event.getCommentId() ==post.getUserId() ) {
            return;

            // MSA 아키텍처이므로 post 서버에서 받아와야한다.
            // 가령 REST API 로 호출
        }

        Comment comment = commentClient.getComment(event.getCommentId());

        Notification notification = createNotification(post,comment);
        // 알림 생성

        saveService.insert(notification);
        // 저장

    }

    private Notification createNotification(Post post,Comment comment) {

        Instant now = Instant.now();
        //동일해야하므로 변수할당
        return new CommentNotification(
            NotificationIdGenerator.generateId(),
            post.getUserId(),
            NotificationType.COMMENT,
            comment.getCreatedAt(),
            now,
            now,
            now.plus(90, ChronoUnit.DAYS),
            post.getId(),
            post.getUserId(),
            post.getContent(),
            comment.getId()
        );
    }

}
