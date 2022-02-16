package com.pio.apiserver.application;

import com.pio.apiserver.domain.Post;
import com.pio.apiserver.dto.PostCreationData;
import com.pio.apiserver.dto.PostUpdateData;
import com.pio.apiserver.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DisplayName("PostService")
class PostServiceTest {
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void prepare() {
        postService = new PostService(postRepository);
    }

    @Nested
    @DisplayName("create 메소드")
    class Describe_create {
        @Nested
        @DisplayName("포스트 등록정보가 주어진다면")
        class Context_with_new_post {
            static final String NEW_POST_TEXT = "new";
            PostCreationData postCreationData;

            @BeforeEach
            void prepare() {
                postCreationData = PostCreationData
                        .builder()
                        .text(NEW_POST_TEXT)
                        .build();
            }

            @Test
            @DisplayName("포스트를 등록하고 리턴한다")
            void it_returns_created_post() {
                Post result = postService.create(postCreationData);
                assertThat(result.getText()).isEqualTo(postCreationData.getText());
            }
        }
    }

    @Nested
    @DisplayName("detail 메소드")
    class Describe_detail {
        @Nested
        @DisplayName("포스트 id가 주어진다면")
        class Context_with_post_id {
            static final String EXISTED_POST_TEXT = "new post";
            Post existed_post;

            @BeforeEach
            void prepare() {
                existed_post = Post.builder().text(EXISTED_POST_TEXT).build();
                postRepository.save(existed_post);
            }

            @Test
            @DisplayName("해당하는 id의 포스트 정보를 리턴한다.")
            void it_returns_post() {
                Post result = postService.detail(existed_post.getId());
                assertThat(result.getId()).isEqualTo(existed_post.getId());
                assertThat(result.getText()).isEqualTo(existed_post.getText());
            }
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {
        @Nested
        @DisplayName("포스트 수정 정보가 주어진다면")
        class Context_with_update_post {
            static final String EXISTED_POST_TEXT = "new post";
            static final String UPDATE_POST_TEXT = "new post";
            Post existed_post;
            PostUpdateData postUpdateData

            @BeforeEach
            void prepare() {
                existed_post = Post.builder().text(EXISTED_POST_TEXT).build();
                postRepository.save(existed_post);

                // TODO : 이미지 수정 테스트를 위해 이미지 저장 필요

                postUpdateData = PostUpdateData.builder()
                        .id(existed_post.getId())
                        .text(UPDATE_POST_TEXT)
                        .build();
            }

            @Test
            @DisplayName("포스트를 수정하고 리턴한다.")
            void it_returns_updated_post() {
                Post result = postService.update(postUpdateData);
                assertThat(result.getText()).isEqualTo(postUpdateData.getText());
                // TODO : 이미지 수정 테스트 필요
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드")
    class Describe_delete {
        @Nested
        @DisplayName("포스트 id가 주어진다면")
        class Context_with_post_id {
            static final String EXISTED_POST_TEXT = "new post";
            Post existed_post;

            @BeforeEach
            void prepare() {
                existed_post = Post.builder().text(EXISTED_POST_TEXT).build();
                postRepository.save(existed_post);
            }

            @Test
            @DisplayName("해당하는 id의 포스트를 삭제하고 리턴한다.")
            void it_returns_deleted_post() {
                Post result = postService.delete(existed_post.getId());
                assertThat(result.getId()).isEqualTo(existed_post.getId());
                assertThat(postRepository.existsById(result.getId())).isFalse();
            }
        }
    }
}