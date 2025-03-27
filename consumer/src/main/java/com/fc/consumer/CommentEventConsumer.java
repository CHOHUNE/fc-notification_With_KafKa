package com.fc.consumer;

import com.fc.event.CommentEvent;
import com.fc.event.CommentEventType;
import com.fc.task.CommendRemoveTask;
import com.fc.task.CommentAddTask;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommentEventConsumer {

    @Autowired
    CommentAddTask commentAddTask;
    @Autowired

    CommendRemoveTask commendRemoveTask;

    @Bean(name = "comment")
    public Consumer<CommentEvent> comment() {
        return event ->{
            if (event.getType() == CommentEventType.ADD) {
                commentAddTask.processEvent(event);
            } else if (event.getType() == CommentEventType.REMOVE) {
                commendRemoveTask.processEvent(event);
            }
        };
    }
}
