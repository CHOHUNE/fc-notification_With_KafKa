package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class NotificationSaveService {

    @Autowired
    private NotificationRepository repository;


    public void insert(Notification notification) {
        Notification result = repository.insert(notification); // 기존 entities 에 insert 될 entity 가 없을 때 쓴다
        log.info("inserted: {}",result);
    }

    public void upsert(Notification notification) { // update && insert
        Notification result = repository.save(notification); // save 는 기존 entities 에 entity 가 있으면 update or insert 를 수행한다

        log.info("upserted : {}", result);
    }






}
