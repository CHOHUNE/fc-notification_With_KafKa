package com.fc.event;


import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FollowEventConsumer {

    @Bean(name = "follow")
    public Consumer<FollowEvent> follow() {

        return event-> log.info(event.toString());
    }
}
