package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchProductRepository;
import com.nhnacademy.marketgg.client.service.SearchProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultSearchProductService implements SearchProductService {

    private final SearchProductRepository searchProductRepository;

    @Override
    public List<SearchProductResponse> searchForCategory(final String code,
                                                         final SearchRequest request)
            throws ParseException, JsonProcessingException {

        return searchProductRepository.searchProductForCategory(code, request);
    }

    @Override
    public List<SearchProductResponse> searchForKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        return searchProductRepository.searchProductWithKeyword(request);
    }

    @Override
    public List<SearchProductResponse> searchForCategorySortPrice(final String code,
                                                                  final SearchRequest request,
                                                                  final String type)
            throws ParseException, JsonProcessingException {

        return searchProductRepository.searchProductForCategorySortPrice(code, request, type);
    }

    @Override
    public List<SearchProductResponse> searchForKeywordSortPrice(final SearchRequest request,
                                                                 final String type)
            throws ParseException, JsonProcessingException {

        return searchProductRepository.searchProductForKeywordSortPrice(request, type);
    }

}

