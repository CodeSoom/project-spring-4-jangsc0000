package com.pio.apiserver.dto;

import com.pio.apiserver.domain.Image;
import com.pio.apiserver.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostCreationData {
    private String text;

    public Post createPost(List<Image> images) {
        return Post.builder()
                .text(text)
                .images(images)
                .build();
    }
}



