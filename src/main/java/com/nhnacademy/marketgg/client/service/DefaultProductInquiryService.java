package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.ProductInquiryRepository;
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
    public void createInquiry(Long productId, ProductInquiryRequest inquiryRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryRepository.createInquiry(productId, inquiryRequest);
    }

    @Override
    public List<ProductInquiryResponse> retrieveInquiryByProduct(Long productId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        return this.inquiryRepository.retrieveAllInquiry(productId);
    }

    @Override
    public List<ProductInquiryResponse> retrieveInquiryByMember()
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        return this.inquiryRepository.retrieveAllInquiry();
    }

    @Override
    public void deleteProductInquiry(Long productId, Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryRepository.deleteInquiry(productId, inquiryId);
    }

}
