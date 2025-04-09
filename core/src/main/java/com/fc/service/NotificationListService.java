package com.fc.service;


import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationListService {

    private final NotificationRepository notificationRepository;


    // 목록 조회 Pivot Vs Paging
    // Pivot 은 기준점을 가지고 구분하는 것
    // pivot 은 기준점 occurredAt 이후로 size 몇을 줘라
    // paging 은 조회 중 알람이 들어오면 알람 페이지가 넘어가버리므로 알람엔 부적절함
    // sns 나 실시간성이 강한 data 성격엔 쓰이지 않음
    public Slice<Notification> getUserNotificationByPivot(Long userId, Instant occurredAt) {

        if (occurredAt == null) {
            return notificationRepository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(
                userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        } else {

            return notificationRepository.findAllByUserIdOrderByOccurredAtDesc(userId,
                PageRequest.of(0, PAGE_SIZE));

        }
    }

    private static final int PAGE_SIZE = 20;

}
