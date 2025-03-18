package com.fc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootApplication // 스프링 부트 진입점 제공 1. 컴포넌트 스캔 2. 스프링 부트 애플리케이션 설정 사용
@SpringBootTest // 테스트 관련 어노테이션
    // 두 어노테이션을 같이 쓰면 통합 테스트
class NotificationRepositoryMemoryImplTest {

    @Autowired
    private  NotificationRepository sut;

    private final Long userId = 2L;
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

        sut.save(new Notification("1", userId, NotificationType.LIKE, now,
            deletedAt));

        Optional<Notification> notification = sut.findById("1");

        assertTrue(notification.isPresent());
    }

    @Test
    void test_find_by_id() {

        sut.save(new Notification("2", userId, NotificationType.LIKE, now,
            deletedAt));

        Optional<Notification> byId = sut.findById("2");

        Notification notification = byId.orElseThrow();

        assertEquals(notification.id,"2");
        assertEquals(notification.userId,2L);
        assertEquals(notification.createdAt.getEpochSecond(),now.getEpochSecond());
        assertEquals(notification.deletedAt.getEpochSecond(),deletedAt.getEpochSecond());

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