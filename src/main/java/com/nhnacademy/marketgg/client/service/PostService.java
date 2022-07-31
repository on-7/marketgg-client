package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 고객센터 게시글과 관련된 Service 입니다.
 *
 * @version 1.0.0
 */
public interface PostService {

    /**
     * 게시판 타입에 맞는 게시글을 등록할 수 있는 메소드입니다.
     *
     * @param postRequest - 등록할 게시글의 정보를 담은 객체입니다.
     * @param type - 등록할 게시글의 게시판 타입입니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createPost(final PostRequest postRequest, final String type) throws JsonProcessingException;

    /**
     * 게시판 타입에 맞는 게시글 목록을 조회할 수 있는 메소드입니다.
     *
     * @param page - 조회할 게시글 목록의 페이지 정보입니다.
     * @param type - 조회할 게시글의 게시판 목록입니다.
     * @return 게시판 타입에 맞는 게시글 목록들을 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrievesPostList(final Integer page, final String type);

    List<PostResponse> retrievesPostListForMe(final Integer page, final String type, final MemberInfo memberInfo);

    /**
     * 지정한 게시글의 상세정보를 조회할 수 있는 메소드입니다.
     *
     * @param boardNo - 지정한 게시글의 식별번호입니다.
     * @param type - 지정한 게시글의 게시판 타입입니다.
     * @return 지정한 게시글의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponse retrievePost(final Long boardNo, final String type);

    /**
     * 지정한 1:1 문의의 상세정보를 조회 할 수 있는 메소드입니다.
     *
     * @param boardNo - 지정한 1:1문의의 식별번호입니다.
     * @param type - 지정한 1:1문의의 게시판 타입입니다.
     * @return 지정한 1:1 문의의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long boardNo, final String type);

    /**
     * 지정한 게시글을 수정할 수 있는 메소드입니다.
     *
     * @param boardNo - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 게시글의 정보를 담은 객체입니다.
     * @param type - 수정할 게시글의 게시판 타입입니다.
     * @since 1.0.0
     */
    void updatePost(final Long boardNo, final PostRequest postRequest, final String type);

    /**
     * 지정한 게시글을 삭제할 수 있는 메소드입니다.
     *
     * @param boardNo - 삭제할 게시글의 식별번호입니다.
     * @param type - 삭제할 게시글의 게시판 타입입니다.
     * @since 1.0.0
     */
    void deletePost(final Long boardNo, final String type);

}
