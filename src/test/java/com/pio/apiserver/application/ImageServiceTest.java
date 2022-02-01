package com.pio.apiserver.application;

import com.pio.apiserver.domain.Image;
import com.pio.apiserver.dto.ImageCreationData;
import com.pio.apiserver.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ImageService")
class ImageServiceTest {
    ImageService imageService;
    S3Service s3Service;

    @Autowired
    ImageRepository imageRepository;
    
    @BeforeEach
    void prepareService() {
        s3Service = new S3Service();
        imageService = new ImageService(imageRepository, s3Service);
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        @Nested
        @DisplayName("이미지 등록 정보가 주어지면")
        class Context_with_image {
            static final String NEW_IMAGE_TYPE = "image/jpeg";
            static final byte[] NEW_IMAGE_BINS = {0x48, 0x65, 0x6C, 0x6C, 0x6f, 0x20, 0x57, 0x6f, 0x72, 0x6c, 0x64};

            ImageCreationData imageCreationData;

            @BeforeEach
            void prepare() {
                // TODO : post를 실제 생성하여, 생성된 post id로 테스트하도록 변경
                Long post_id = 1L;
                imageCreationData = ImageCreationData.builder()
                        .contentType(NEW_IMAGE_TYPE)
                        .postId(post_id)
                        .image(NEW_IMAGE_BINS)
                        .build();
            }

            @Test
            @DisplayName("이미지를 등록하고 리턴한다.")
            void it_returns_image() {
                Image result = imageService.create(imageCreationData);
                assertThat(result.getImageUrl()).isNotBlank();
                assertThat(result.getPostId()).isEqualTo(imageCreationData.getPostId());
                assertThat(result.getContentType()).isEqualTo(imageCreationData.getContentType());
            }
        }
    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_detail {
        Image existed_image;

        @BeforeEach
        void prepare() {
            existed_image = Image.builder().build();
            imageRepository.save(existed_image);
        }

        @Nested
        @DisplayName("이미지 id가 주어지면")
        class Context_with_image_id {
            @Test
            @DisplayName("해당 id의 이미지 정보를 리턴한다.")
            void it_returns_image() {
                Image result = imageService.detail(existed_image.getId());
                assertThat(result.getId()).isEqualTo(existed_image.getId());
                assertThat(result.getPostId()).isEqualTo(existed_image.getPostId());
                assertThat(result.getImageUrl()).isEqualTo(existed_image.getImageUrl());
                assertThat(result.getContentType()).isEqualTo(existed_image.getContentType());
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        Image existed_image;

        @BeforeEach
        void prepare() {
            existed_image = Image.builder().build();
            imageRepository.save(existed_image);
        }

        @Nested
        @DisplayName("이미지 id가 주어지면")
        class Context_with_image_id {
            @Test
            @DisplayName("해당 id의 이미지 정보를 삭제하고 리턴한다.")
            void it_returns_image() {
                Image result = imageService.delete(existed_image.getId());
                assertThat(result.getId()).isEqualTo(existed_image.getId());
                assertThat(result.getPostId()).isEqualTo(existed_image.getPostId());
                assertThat(result.getImageUrl()).isEqualTo(existed_image.getImageUrl());
                assertThat(result.getContentType()).isEqualTo(existed_image.getContentType());
            }
        }
    }
}