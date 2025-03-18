package com.fc;


import java.time.Instant;
import lombok.AllArgsConstructor;

enum NotificationType{
LIKE,
COMMENT,
FOLLOW
}

@AllArgsConstructor
public class Notification {

    public String id;
    public Long userId;
    public NotificationType type;
    public Instant createdAt;
    public Instant deletedAt;

}
