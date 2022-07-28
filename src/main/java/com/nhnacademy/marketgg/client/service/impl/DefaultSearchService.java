package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchRepository;
import com.nhnacademy.marketgg.client.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultSearchService implements SearchService {

    private final SearchRepository searchRepository;

    @Override
    public List<SearchProductResponse> searchProductForCategory(final String optionCode,
                                                                final SearchRequest request,
                                                                final String priceSortType)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchProductForCategory(optionCode, request, priceSortType);
    }

    @Override
    public List<SearchProductResponse> searchProductForKeyword(final SearchRequest request,
                                                               final String priceSortType)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchProductWithKeyword(request, priceSortType);
    }

    @Override
    public List<SearchBoardResponse> searchBoardForOption(final String optionCode,
                                                          final SearchRequest request,
                                                          final String option)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchBoardWithOption(optionCode, request, option);
    }

    @Override
    public List<SearchBoardResponse> searchBoardForKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchBoardWithKeyword(request);
    }

}

