package com.fc;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/*
* 추후에 실제 Post 서버를 구축하게 되면 해당 클래스를 변경해야 하므로
* 일단 interface 로 만든 뒤 이를 impl 하는 class 를 임시로 만든다
* 추후 실제 Post Class 로 교체한다
*
* 하지만 -> 여기서는 그냥 바로 작성하는 것으로
* */
@Component
public class PostClient {

    private final Map<Long, Post> post = new HashMap< >();

    public PostClient() {
        post.put(1L, new Post(1L,11L,"URL1","content1"));
        post.put(2L, new Post(2L,22L,"URL2","content2"));
        post.put(3L, new Post(3L,33L,"URL3","content3"));
    }

    public Post getPost(Long id) {
        return post.get(id);
    }
}
