package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
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
    public List<SearchProductResponse> searchForCategory(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchProductForCategory(request);
    }

    @Override
    public List<SearchProductResponse> searchForKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchProductWithKeyword(request);
    }

}
