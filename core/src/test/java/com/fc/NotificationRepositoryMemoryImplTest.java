package com.fc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NotificationRepositoryMemoryImplTest {

    private final NotificationRepositoryMemoryImpl sut = new NotificationRepositoryMemoryImpl();

    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90,ChronoUnit.DAYS);

    @Test
    @DisplayName("저장")
    void test_save() {

        //when
        //then
        //case

        // 알림 객체 생성
        // 저장
        // 조회 했을 때 객체가 있나?

        sut.save(new Notification("1", 2L, NotificationType.LIKE, now,
            deletedAt));

        Optional<Notification> notification = sut.findById("1");

        assertTrue(notification.isPresent());
    }

    @Test
    void test_find_by_id() {

        sut.save(new Notification("2", 2L, NotificationType.LIKE, now,
            deletedAt));

        Optional<Notification> byId = sut.findById("2");

        Notification notification = byId.orElseThrow();

        assertEquals(notification.id,"2");
        assertEquals(notification.userId,2L);
        assertEquals(notification.createdAt,now);
        assertEquals(notification.deletedAt,deletedAt);

    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
        sut.save(new Notification("3", 3L, NotificationType.LIKE, now, deletedAt));
        sut.deleteById("3");

        Optional<Notification> optionalNotification = sut.findById("3");

        assertFalse(optionalNotification.isPresent());
    }
}