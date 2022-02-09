package com.pio.apiserver.dto;

import com.pio.apiserver.domain.Image;
import com.pio.apiserver.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostUpdateData {
    private Long id;
    private String text;
    private List<Image> images;

    public Post createPost(List<Image> images) {
        images.addAll(images);
        return Post.builder()
                .id(id)
                .text(text)
                .images(images)
                .build();
    }
}