package com.pio.apiserver.application;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.pio.apiserver.config.AmazonS3Config;
import com.pio.apiserver.domain.Image;
import com.pio.apiserver.domain.Post;
import com.pio.apiserver.dto.ImageCreationData;
import com.pio.apiserver.repository.ImageRepository;
import com.pio.apiserver.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DataJpaTest
@DisplayName("ImageService")
class ImageServiceTest {
    static final String EXISTED_IMAGE_TYPE = "image/jpeg";
    static final String EXISTED_IMAGE_URL = "https://test.com/image.jpg";

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostRepository postRepository;

    ImageService imageService;

    S3Uploader s3Uploader;

    @BeforeEach
    void prepareService() {
        s3Uploader = mock(S3Uploader.class);
        imageService = new ImageService(imageRepository, s3Uploader);
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        @Nested
        @DisplayName("이미지 등록 정보가 주어지면")
        class Context_with_image {
            static final String NEW_IMAGE_TYPE = "image/jpeg";
            static final byte[] NEW_IMAGE_BINS = {0x48, 0x65, 0x6C, 0x6C};
            static final String NEW_IMAGE_URL = "https://test.com/image.jpg";

            ImageCreationData imageCreationData;
            Post post;

            @BeforeEach
            void prepare() {
                post = postRepository.save(Post.builder().build());

                imageCreationData = ImageCreationData.builder()
                        .contentType(NEW_IMAGE_TYPE)
                        .image(NEW_IMAGE_BINS)
                        .build();

                given(s3Uploader.upload(imageCreationData.getImage(), imageCreationData.getContentType())).willReturn(NEW_IMAGE_URL);
            }

            @Test
            @DisplayName("이미지를 등록하고 리턴한다.")
            void it_returns_image() {
                Image result = imageService.create(post.getId(), imageCreationData);
                assertThat(result.getImageUrl()).isEqualTo(NEW_IMAGE_URL);
                assertThat(result.getPostId()).isEqualTo(post.getId());
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
            Post post = postRepository.save(Post.builder().build());

            existed_image = Image.builder()
                    .postId(post.getId())
                    .imageUrl(EXISTED_IMAGE_URL)
                    .contentType(EXISTED_IMAGE_TYPE)
                    .build();
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
            Post post = postRepository.save(Post.builder().build());

            existed_image = Image.builder()
                    .postId(post.getId())
                    .imageUrl(EXISTED_IMAGE_URL)
                    .contentType(EXISTED_IMAGE_TYPE)
                    .build();
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