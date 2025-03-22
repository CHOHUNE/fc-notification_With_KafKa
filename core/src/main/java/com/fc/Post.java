package com.fc;

/*
* 추후 이관 예정
*
* */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class Post {

    private Long id;
    private Long userId;
    private String imgUrl;
    private String content;

}
