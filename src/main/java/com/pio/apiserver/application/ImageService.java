package com.pio.apiserver.application;

import com.pio.apiserver.domain.Image;
import com.pio.apiserver.dto.ImageCreationData;
import com.pio.apiserver.repository.ImageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 이미지 등록, 조회, 삭제를 담당하는 서비스입니다.
 */
@Service
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    public ImageService(ImageRepository imageRepository, S3Uploader s3Uploader) {
        this.imageRepository = imageRepository;
        this.s3Uploader = s3Uploader;
    }

    /**
     * 주어진 이미지 등록 정보로 이미지를 등록하고, 등록된 이미지 정보를 리턴합니다.
     *
     * @param postId            연결할 포스트 id
     * @param imageCreationData 이미지 등록 정보
     * @return 등록된 이미지 정보
     */
    public Image create(Long postId, ImageCreationData imageCreationData) {
        String url = s3Uploader.upload(
                imageCreationData.getImage(),
                imageCreationData.getContentType());
        Image image = imageCreationData.createImage(postId, url);

        return imageRepository.save(image);
    }

    /**
     * 주어진 이미지 id에 해당하는 이미지 정보를 리턴합니다.
     *
     * @param id 이미지 id
     * @return 이미지 정보
     */
    public Image detail(Long id) {
        // TODO : NotFoundException 추가
        Image image = imageRepository.findById(id).orElseThrow();
        return image;
    }

    /**
     * 주어진 이미지 id에 해당하는 이미지를 삭제하고 리턴합니다.
     *
     * @param id 이미지 id
     * @return 삭제된 이미지 정보
     */
    public Image delete(Long id) {
        // TODO : NotFoundException 추가
        Image image = imageRepository.findById(id).orElseThrow();
        imageRepository.delete(image);
        return image;
    }
}
