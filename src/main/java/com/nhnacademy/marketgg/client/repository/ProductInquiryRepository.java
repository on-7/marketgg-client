package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;

/**
 * 상품 문의 관련 데이터를 처리합니다.
 */
public interface ProductInquiryRepository {

    /**
     * 관리자가 상품 문의에 남긴 답글을 업데이트하는 Adapter 입니다.
     *
     * @param replyRequest  상품 문의에 등록할 답글 정보를 담고 있습니다.
     * @throws JsonProcessingException   응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException      인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException  권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void updateInquiryReply(final ProductInquiryReplyRequest replyRequest) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 상품 문의를 등록하는 Adapter 입니다.
     *
     * @param productId       상품 문의를 남길 상품 Id 입니다.
     * @param inquiryRequest  상품 문의에 필요한 정보를 담은 객체입니다.
     * @throws JsonProcessingException   응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException      인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException  권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void createInquiry(final Long productId, final ProductInquiryRequest inquiryRequest) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 상품 번호로 문의를 조회하는 Adapter 입니다.
     *
     * @param productId  조회할 상품 번호입니다.
     * @return 조회한 상품 문의 결과를 반환합니다.
     * @throws JsonProcessingException   응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException      인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException  권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageResult<ProductInquiryResponse> retrieveAllInquiry(final Integer page, final Long productId) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * MY GG 에서 회원이 남긴 상품 문의를 조회하는 Adapter 입니다.
     *
     * @return 조회한 상품 문의 결과를 반환합니다.
     * @throws JsonProcessingException   응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException      인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException  권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageResult<ProductInquiryResponse> retrieveAllInquiry(final Integer page) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 상품 문의를 삭제하는 Adapter 입니다.
     *
     * @param productId  삭제할 문의의 상품 번호입니다.
     * @param inquiryId  삭제할 문의 번호입니다.
     * @throws JsonProcessingException   응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException      인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException  권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    void deleteInquiry(final Long productId, final Long inquiryId) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 관리자가 모든 상품 문의를 조회하는 Adapter 입니다.
     *
     * @param page 조회할 상품의 페이지입니다.
     * @return     조회한 상품 문의 결과를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    PageResult<ProductInquiryResponse> retrieveInquiriesByAdmin(final Integer page);
}
