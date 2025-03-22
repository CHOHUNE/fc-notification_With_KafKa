package com.fc.event;


import java.time.Instant;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class LikeEvent {

    private LikeEventType type;
    private Long postId;
    private Long userId;
    private Instant createdAt;




}
