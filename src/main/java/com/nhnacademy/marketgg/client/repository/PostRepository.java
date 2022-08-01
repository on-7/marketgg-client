package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;

import java.util.List;

/**
 * 고객센터 게시글과 관련된 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface PostRepository {

    /**
     * 입력받은 정보로 게시글을 등록할 수 있는 Adapter 입니다.
     *
     * @param postRequest - 등록할 게시글의 정보를 담은 객체입니다.
     * @param type - 등록할 게시글의 게시판 타입입니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createPost(final PostRequest postRequest, final String type) throws JsonProcessingException;

    /**
     * 게시판 타입에 맞는 게시글 목록을 반환하는 Adapter 입니다.
     *
     * @param page - 반환받을 게시글 목록의 페이지 정보입니다.
     * @param type - 반환받을 게시글 목록의 게시판 타입입니다.
     * @return 게시판 타입에 맞는 게시글 목록을 반환합니다.
     * @since 1.0.0
     */
    List<PostResponseForDetail> retrievesPostList(final Integer page, final String type);

    /**
     * 로그인 한 회원이 등록한 1:1 문의 게시글 목록만을 반환합니다.
     *
     * @param page - 조회할 게시글 목록의 페이지 정보입니다.
     * @param type - 조회할 게시글의 게시판타입입니다.
     * @param memberInfo - 로그인한 회원의 정보입니다.
     * @return 자신이 등록한 1:1문의 게시글 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    List<PostResponseForDetail> retrievesPostListForMe(final Integer page, final String type, final MemberInfo memberInfo) throws JsonProcessingException;

    /**
     * 게시판 타입에 맞는 지정한 게시글의 상세정보를 반환하는 Adapter 입니다.
     *
     * @param postNo - 지정한 게시글의 식별번호입니다.
     * @param type - 지정한 게시글의 게시판 타입입니다.
     * @return 지정한 게시글의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForDetail retrievePost(final Long postNo, final String type);

    /**
     * 1:1문의의 상세정보를 반환하는 Adapter 입니다.
     *
     * @param postNo - 지정한 1:1문의의 식별번호입니다.
     * @param type - 지정한 1:1문의의 게시판 타입입니다.
     * @return 지정한 1:1문의의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long postNo, final String type);

    /**
     * 입력받은 정보로 지정한 게시글을 수정할 수 있는 Adapter 입니다.
     *
     * @param postNo - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 게시글의 정보를 담은 객체입니다.
     * @param type - 수정할 게시글의 게시판 타입입니다.
     * @since 1.0.0
     */
    void updatePost(final Long postNo, final PostRequest postRequest, final String type);

    /**
     * 지정한 게시글을 삭제할 수 있는 Adapter 입니다.
     *
     * @param postNo - 삭제한 게시글의 식별번호입니다.
     * @param type - 삭제할 게시글의 게시판 타입입니다.
     * @since 1.0.0
     */
    void deletePost(final Long postNo, final String type);

    /**
     * 1:1 문의의 사유목록을 반환합니다.
     *
     * @return 1:1문의의 사유들을 반환합니다.
     * @since 1.0.0
     */
    List<String> retrieveReason();

}
