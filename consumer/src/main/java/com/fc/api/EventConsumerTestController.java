package com.fc.api;


import com.fc.event.CommentEvent;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventConsumerTestController implements EventConsumerTestControllerSpec{

    @Autowired
    private Consumer<CommentEvent> comment;

    @Override
    @PostMapping("/test/comment")
    public void comment(@RequestBody  CommentEvent event) {
        comment.accept(event);
    }

}
