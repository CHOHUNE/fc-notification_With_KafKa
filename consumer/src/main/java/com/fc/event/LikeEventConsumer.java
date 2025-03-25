package com.fc.event;

import static com.fc.event.LikeEventType.*;

import com.fc.task.LikeAddTask;
import com.fc.task.LikeRemoveTask;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LikeEventConsumer {


    @Autowired
    private LikeAddTask likeAddTask;
    @Autowired
    private LikeRemoveTask likeRemoveTask;

    @Bean(name="like")
    public Consumer<LikeEvent> like() {

        return event-> {
            if (event.getType().equals(ADD)) {
                likeAddTask.processEvent(event);
            } else if (event.getType().equals(REMOVE)) {
                likeRemoveTask.processEvent(event);
            }
        };
    }

}
