package com.fc.event;

import java.time.Instant;

public class FollowEvent {

    private FollowEvent type;
    private Long userId;
    private Long targetUserId;
    private Instant createdAt;

}
