package com.fc.task;


import com.fc.NotificationGetService;
import com.fc.NotificationRemoveService;
import com.fc.NotificationType;
import com.fc.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FollowRemoveTask {

    private final NotificationGetService notificationGetService;
    private final NotificationRemoveService notificationRemoveService;

    public void processEvent(FollowEvent followEvent) {

        if (followEvent.getUserId().equals(followEvent.getTargetUserId())) {

            log.error(" targetUser and userId cannot be the same");
            return;
        }

        notificationGetService.getNotificationByTypeAndFollowerId(NotificationType.FOLLOW,
            followEvent.getTargetUserId(), followEvent.getUserId()).ifPresent(
            notification -> notificationRemoveService.deleteById(notification.getId()));
    }
}
