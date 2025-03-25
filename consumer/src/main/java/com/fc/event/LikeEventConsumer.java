package com.fc.event;

import com.fc.task.LikeAddTask;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LikeEventConsumer {


    private LikeAddTask likeAddTask;

    @Bean(name="like")
    public Consumer<LikeEvent> like() {

        return event-> {
            if (event.getType().equals(LikeEventType.ADD)) {

                likeAddTask.processEvent(event);

            }
        };
    }

}
