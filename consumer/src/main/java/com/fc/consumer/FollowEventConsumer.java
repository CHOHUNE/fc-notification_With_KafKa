package com.fc.consumer;


import com.fc.event.FollowEvent;
import com.fc.event.FollowEventType;
import com.fc.task.FollowAddTask;
import com.fc.task.FollowRemoveTask;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowEventConsumer {

    private final FollowAddTask followAddTask;
    private final FollowRemoveTask followRemoveTask;

    @Bean(name = "follow")
    public Consumer<FollowEvent> follow() {

        return event -> {
            if (event.getType().equals(FollowEventType.ADD)) {

                followAddTask.processEvent(event);
            } else if (event.getType().equals(FollowEventType.REMOVE)) {

                followRemoveTask.processEvent(event);
            }

        };
    }
}
