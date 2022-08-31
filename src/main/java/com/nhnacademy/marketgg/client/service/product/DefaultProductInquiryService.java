package com.nhnacademy.marketgg.client.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.product.ProductInquiryRepository;
import com.nhnacademy.marketgg.client.service.product.ProductInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductInquiryService implements ProductInquiryService {

    private final ProductInquiryRepository inquiryRepository;

    @Override
    public void updateReply(final ProductInquiryReplyRequest replyRequest, final Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryRepository.updateInquiryReply(replyRequest, inquiryId);
    }

    @Override
    public void createInquiry(final Long productId, final ProductInquiryRequest inquiryRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryRepository.createInquiry(productId, inquiryRequest);
    }

    @Override
    public PageResult<ProductInquiryResponse> retrieveInquiryByProduct(final Integer page, final Long productId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        return this.inquiryRepository.retrieveAllInquiry(page, productId);
    }

    @Override
    public PageResult<ProductInquiryResponse> retrieveInquiryByMember(final Integer page)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        return this.inquiryRepository.retrieveAllInquiry(page);
    }

    @Override
    public void deleteProductInquiry(final Long productId, final Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryRepository.deleteInquiry(productId, inquiryId);
    }

    @Override
    public PageResult<ProductInquiryResponse> retrieveInquiries(final Integer page) {
        return this.inquiryRepository.retrieveInquiriesByAdmin(page);
    }

}
