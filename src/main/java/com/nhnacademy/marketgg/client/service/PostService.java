package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import java.util.List;

/**
 * 고객센터 게시글과 관련된 Service 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface PostService {

    /**
     * 게시판 타입에 맞는 게시글을 등록할 수 있는 메소드입니다.
     *
     * @param postRequest - 등록할 게시글의 정보를 담은 객체입니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createPost(final PostRequest postRequest) throws JsonProcessingException;

    /**
     * 게시판 타입에 맞는 게시글 목록을 조회할 수 있는 메소드입니다.
     *
     * @param categoryid - 조회할 게시글 목록의 게시판 카테고리 식별번호입니다.
     * @param page         - 조회할 게시글 목록의 페이지 정보입니다.
     * @return 게시판 타입에 맞는 게시글 목록들을 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrievePostList(final String categoryid, final Integer page);

    /**
     * 지정한 게시글의 상세정보를 조회할 수 있는 메소드입니다.
     *
     * @param postId       - 지정한 게시글의 식별번호입니다.
     * @param categoryid - 지정한 게시글의 게시판 카테고리 식별번호입니다.
     * @return 지정한 게시글의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForDetail retrievePost(final Long postId, final String categoryid);

    /**
     * 지정한 카테고리 내에서 검색합니다.
     *
     * @param categoryid  - 지정한 카테고리의 식별번호입니다.
     * @param searchRequest - 검색을 진행할 검색 정보를 담은 객체입니다.
     * @return 검색한 결과 목록을 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> searchForCategory(final String categoryid, final SearchRequest searchRequest);

    /**
     * 지정한 카테고리 내에서 지정한 옵션내로 검색합니다.
     *
     * @param categoryid  - 지정한 카테고리의 식별번호입니다.
     * @param searchRequest - 검색을 진행할 검색 정보를 담은 객체입니다.
     * @param optionType    - 검색을 진행할 옵션입니다.
     * @param option        - 검색을 진행할 옵션의 값입니다.
     * @return 검색한 결과 목록을 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> searchForOption(final String categoryid, final SearchRequest searchRequest,
                                       final String optionType, final String option);

    /**
     * 지정한 게시글을 수정할 수 있는 메소드입니다.
     *
     * @param postId       - 수정할 게시글의 식별번호입니다.
     * @param postRequest  - 수정할 게시글의 정보를 담은 객체입니다.
     * @param categoryid - 수정할 게시글의 게시판 카테고리 식별번호입니다.
     * @since 1.0.0
     */
    void updatePost(final Long postId, final PostRequest postRequest, final String categoryid)
            throws JsonProcessingException;

    /**
     * 지정한 게시글을 삭제할 수 있는 메소드입니다.
     *
     * @param postId       - 삭제할 게시글의 식별번호입니다.
     * @param categoryid - 수정할 게시글의 게시판 카테고리 식별번호입니다.
     * @since 1.0.0
     */
    void deletePost(final Long postId, final String categoryid);

    /**
     * 1:1 문의에서 사용되는 사유목록을 반환합니다.
     *
     * @return 사유 목록을 반환합니다.
     * @since 1.0.0
     */
    List<String> retrieveOtoReason();

    /**
     * 지정한 1:1 문의의 상태를 입력해 변경 할 수 있습니다.
     *
     * @param postId      - 상태를 변경할 게시판의 식별번호입니다.
     * @param postRequest - 게시판의 상태를 변경 할 정보를 담은 객체입니다.
     * @throws JsonProcessingException JSON 과 관련한 파싱 예외처리입니다.
     * @since 1.0.0
     */
    void changeStatus(final Long postId, final PostStatusUpdateRequest postRequest) throws JsonProcessingException;

    /**
     * 1:1 문의에서 사용되는 상태 목록을 반환합니다.
     *
     * @return 상태 목록을 반환합니다.
     * @since 1.0.0
     */
    List<String> retrieveOtoStatus();

}
