package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.dto.response.SearchBoardResponse;
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
     * @param type        - 등록할 게시글의 게시판 타입입니다.
     * @param role        - 권한을 표시합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createPost(final PostRequest postRequest, final String type, final String role) throws JsonProcessingException;

    /**
     * 게시판 타입에 맞는 게시글 목록을 조회할 수 있는 메소드입니다.
     *
     * @param page - 조회할 게시글 목록의 페이지 정보입니다.
     * @param type - 조회할 게시글의 게시판 목록입니다.
     * @param role - 권한을 표시합니다.
     * @return 게시판 타입에 맞는 게시글 목록들을 반환합니다.
     * @since 1.0.0
     */
    List<PostResponse> retrievesPostList(final Integer page, final String type, final String role);

    /**
     * 로그인 한 회원이 작성한 1:1 문의 게시글 목록을 조회합니다.
     *
     * @param page       - 조회할 게시글 목록의 페이지 정보입니다.
     * @param type       - 조회할 게시글 목록의 게시판 타입입니다.
     * @return 로그인한 회원이 작성한 1:1 문의 게시글 목록을 반환합니다.
     */
    List<PostResponse> retrievesPostListForMe(final Integer page, final String type) ;

    /**
     * 지정한 게시글의 상세정보를 조회할 수 있는 메소드입니다.
     *
     * @param postNo - 지정한 게시글의 식별번호입니다.
     * @param type   - 지정한 게시글의 게시판 타입입니다.
     * @param role   - 권한을 표시합니다.
     * @return 지정한 게시글의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForDetail retrievePost(final Long postNo, final String type, final String role);

    /**
     * 지정한 1:1 문의의 상세정보를 조회 할 수 있는 메소드입니다.
     *
     * @param postNo - 지정한 1:1문의의 식별번호입니다.
     * @param type   - 지정한 1:1문의의 게시판 타입입니다.
     * @param role   - 권한을 표시합니다.
     * @return 지정한 1:1 문의의 상세정보를 반환합니다.
     * @since 1.0.0
     */
    PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long postNo, final String type, final String role);

    /**
     * 지정한 카테고리 내에서 검색합니다.
     *
     * @param categoryCode  - 지정한 카테고리의 식별번호입니다.
     * @param searchRequest - 검색을 진행할 검색 정보를 담은 객체입니다.
     * @param role          - 권한을 표시합니다.
     * @return 검색한 결과 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     */
    List<SearchBoardResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest,
                                                final String role)
            throws JsonProcessingException;

    /**
     * 지정한 카테고리 내에서 지정한 사유내로 검색합니다.
     *
     * @param categoryCode  - 지정한 카테고리의 식별번호입니다.
     * @param searchRequest - 검색을 진행할 검색 정보를 담은 객체입니다.
     * @param reason        - 검색을 진행 할 사유를 지정한 값입니다.
     * @param role          - 권한을 표시합니다.
     * @return 검색한 결과 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     */
    List<SearchBoardResponse> searchForReason(final String categoryCode, final SearchRequest searchRequest,
                                              final String reason, final String role) throws JsonProcessingException;

    /**
     * 지정한 카테고리 내에서 지정한 상태내로 검색합니다.
     *
     * @param categoryCode  - 지정한 카테고리의 식별번호입니다.
     * @param searchRequest - 검색을 진행할 검색 정보를 담은 객체입니다.
     * @param status        - 검색을 진행 할 상태를 지정한 값입니다.
     * @param role          - 권한을 표시합니다.
     * @return 검색한 결과 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     */
    List<SearchBoardResponse> searchForStatus(final String categoryCode, final SearchRequest searchRequest,
                                              final String status, final String role) throws JsonProcessingException;

    /**
     * 지정한 게시글을 수정할 수 있는 메소드입니다.
     *
     * @param postNo      - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 게시글의 정보를 담은 객체입니다.
     * @param type        - 수정할 게시글의 게시판 타입입니다.
     * @param role        - 권한을 표시합니다.
     * @since 1.0.0
     */
    void updatePost(final Long postNo, final PostRequest postRequest, final String type, final String role);

    /**
     * 지정한 게시글을 삭제할 수 있는 메소드입니다.
     *
     * @param postNo - 삭제할 게시글의 식별번호입니다.
     * @param type   - 삭제할 게시글의 게시판 타입입니다.
     * @param role   - 권한을 표시합니다.
     * @since 1.0.0
     */
    void deletePost(final Long postNo, final String type, final String role);

    /**
     * 1:1 문의에서 사용되는 사유목록을 반환합니다.
     *
     * @return 사유 목록을 반환합니다.
     * @since 1.0.0
     */
    List<String> retrieveOtoReason();

}
