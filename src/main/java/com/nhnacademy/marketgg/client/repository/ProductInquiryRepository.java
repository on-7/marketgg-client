package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 상품 문의 관련 데이터를 처리합니다.
 */
public interface ProductInquiryRepository {

    /**
     *
     * @param replyRequest - 상품 문의에 등록할 답글 정보를 담고 있습니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void updateInquiryReply(ProductInquiryReplyRequest replyRequest) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     *
     * @param productId - 상품 문의를 남길 상품 Id 입니다.
     * @param inquiryRequest - 상품 문의에 필요한 정보를 담은 객체입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void createInquiry(Long productId, ProductInquiryRequest inquiryRequest) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     *
     * @param productId - 상품
     * @return
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageResult<ProductInquiryResponse> retrieveAllInquiry(final Integer page, final Long productId) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     *
     * @return
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageResult<ProductInquiryResponse> retrieveAllInquiry(final Integer page) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     *
     * @param productId
     * @param inquiryId
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void deleteInquiry(Long productId, Long inquiryId) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

}
