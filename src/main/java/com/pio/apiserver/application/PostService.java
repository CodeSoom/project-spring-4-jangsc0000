package com.pio.apiserver.application;

import com.pio.apiserver.domain.Post;
import com.pio.apiserver.dto.PostCreationData;
import com.pio.apiserver.dto.PostUpdateData;
import com.pio.apiserver.repository.PostRepository;
import org.springframework.stereotype.Service;

/**
 * Post를 관리하는 서비스입니다.
 */
@Service
public class PostService {

    PostRepository postRepository;

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 주어진 포스트 등록 정보로 포스트를 생성하고 리턴합니다.
     *
     * @param postCreationData 포스트 등록정보
     * @return 등록된 포스트 정보
     */
    public Post create(PostCreationData postCreationData) {
    }

    /**
     * 주어진 id에 해당하는 포스트 정보를 리턴합니다.
     *
     * @param id 포스트 id
     * @return 포스트 정보
     */
    public Post detail(Long id) {

    }

    /**
     * 주어진 포스트 수정 정보로 포스트를 수정하고 리턴합니다.
     *
     * @param id             수정할 포스트 id
     * @param postUpdateData 포스트 수정 정보
     * @return 수정된 포스트 정보
     */
    public Post update(PostUpdateData postUpdateData) {
    }

    /**
     * 주어진 id에 해당하는 포스트를 삭제하고 리턴합니다.
     *
     * @param id 포스트 id
     * @return 삭제된 포스트 정보
     */
    public Post delete(Long id) {

    }

}
