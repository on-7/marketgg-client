package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PageRequest;
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
    public List<SearchProductResponse> searchForCategory(final String category, final PageRequest pageRequest)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchProductForCategory(category, pageRequest);
    }

    @Override
    public List<SearchProductResponse> searchForKeyword(final String keyword, final PageRequest pageRequest)
            throws ParseException, JsonProcessingException {

        return searchRepository.searchProductWithKeyword(keyword, pageRequest);
    }

}
