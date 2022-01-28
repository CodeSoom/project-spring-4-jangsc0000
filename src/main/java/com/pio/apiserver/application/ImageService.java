package com.pio.apiserver.application;

import com.pio.apiserver.domain.Image;

/**
 * 이미지 등록, 조회, 삭제를 담당하는 서비스입니다.
 */
public class ImageService {

    /**
     * 주어진 이미지 등록 정보로 이미지를 등록하고, 등록된 이미지 정보를 리턴합니다.
     *
     * @param image 이미지 등록 정보
     * @return 등록된 이미지 정보
     */
    public Image create(ImageCreationData image) {
        // TODO : repository 에 이미지를 등록하고 리턴
        return new Image();
    }

    /**
     * 주어진 이미지 id에 해당하는 이미지 정보를 리턴합니다.
     *
     * @param id 이미지 id
     * @return 이미지 정보
     */
    public Image detail(Long id) {
        // TODO : repository 에서 이미지를 찾아 리턴
        return new Image();
    }

    /**
     * 주어진 이미지 id에 해당하는 이미지를 삭제하고 리턴합니다.
     *
     * @param id 이미지 id
     * @return 삭제된 이미지 정보
     */
    public Image delete(Long id) {
        // TODO : repository 에서 이미지를 삭제하고 리턴
        return new Image();
    }
}
