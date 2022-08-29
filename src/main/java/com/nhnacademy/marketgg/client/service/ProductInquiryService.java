package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;

/**
 * 상품 문의 서비스 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
public interface ProductInquiryService {

    /**
     * 상품 문의에 답글을 등록하기 위해 Adapter 메소드를 실행합니다.
     *
     * @param replyRequest - 상품 문의에 대한 답글을 담은 객체입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void updateReply(final ProductInquiryReplyRequest replyRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 상품 문의를 등록하기 위헤 Adaptor 메소드를 실행합니다.
     *
     * @param productId      - 상품 문의를 남길 상품의 Id 입니다.
     * @param inquiryRequest - 상품 문의에 필요한 정보를 담은 객체입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void createInquiry(final Long productId, final ProductInquiryRequest inquiryRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 한 상품에 대한 전체 상품 문의를 조회하기 위해 Adaptor 메소드를 실행합니다.
     *
     * @param productId - 조회할 상품의 Id 입니다.
     * @return 조회한 상품 문의 정보를 List 로 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageResult<ProductInquiryResponse> retrieveInquiryByProduct(final Integer page, final Long productId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 특정 회원이 남긴 전체 상품 문의를 조회하기 위해 Adaptor 메소드를 실행합니다.
     *
     * @return 조회한 상품 문의 정보를 List 로 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageResult<ProductInquiryResponse> retrieveInquiryByMember(final Integer page)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 상품 문의를 삭제하기 위해 Adaptor 메소드를 실행합니다.
     *
     * @param productId - 삭제할 상품 문의의 상품 Id 입니다.
     * @param inquiryId - 삭제할 상품 문의 Id 입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void deleteProductInquiry(final Long productId, final Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

}
