package com.fc.task;


import com.fc.domain.FollowNotification;
import com.fc.utils.NotificationIdGenerator;
import com.fc.service.NotificationSaveService;
import com.fc.domain.NotificationType;
import com.fc.event.FollowEvent;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class FollowAddTask {


    private final NotificationSaveService saveService;

    public void processEvent(FollowEvent event) {

        if (event.getUserId().equals(event.getTargetUserId())) {
            log.error("targetUserId and userId cannot be the same");
            return;
        }

        saveService.insert( createFollowNotification(event, Instant.now()));
    }

    private static FollowNotification createFollowNotification(FollowEvent event,
        Instant now) {

        return new FollowNotification(
            NotificationIdGenerator.generateId(),
            event.getUserId(),
            NotificationType.FOLLOW,
            event.getCreatedAt(),
            now,
            now,
            now.plus(90, ChronoUnit.DAYS),
            event.getUserId() // 팔로우를 받는 대상자니까 userId 에게 가는 것
        );
    }


}
