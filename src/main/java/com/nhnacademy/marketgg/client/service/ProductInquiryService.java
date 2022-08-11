package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

public interface ProductInquiryService {

    void updateReply(final ProductInquiryReplyRequest replyRequest) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    void createInquiry(Long productId, ProductInquiryRequest inquiryRequest) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    List<ProductInquiryResponse> retrieveInquiryByProduct(Long productId) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    List<ProductInquiryResponse> retrieveInquiryByMember() throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    void deleteProductInquiry(Long productId, Long inquiryId) throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

}
