package com.pio.apiserver.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageCreationData {
    private Long postId;
    private String contentType;
    private byte[] image;
}
