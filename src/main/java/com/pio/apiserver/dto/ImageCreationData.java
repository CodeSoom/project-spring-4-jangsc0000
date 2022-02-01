package com.pio.apiserver.dto;

import com.pio.apiserver.domain.Image;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageCreationData {
    private Long postId;
    private String url;
    private String contentType;
    private byte[] image;

    public Image createImage() {
        return Image.builder()
                .postId(postId)
                .imageUrl(url)
                .contentType(contentType)
                .build();
    }
}
