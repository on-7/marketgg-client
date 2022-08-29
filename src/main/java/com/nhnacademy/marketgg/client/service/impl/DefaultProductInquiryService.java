package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.ProductInquiryRepository;
import com.nhnacademy.marketgg.client.service.ProductInquiryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductInquiryService implements ProductInquiryService {

    private final ProductInquiryRepository inquiryRepository;

    @Override
    public void updateReply(final ProductInquiryReplyRequest replyRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryRepository.updateInquiryReply(replyRequest);
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

}
