package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PageRequest;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface SearchService {

    List<SearchProductResponse> searchForCategory(final String category, final PageRequest pageRequest)
            throws ParseException, JsonProcessingException;

    List<SearchProductResponse> searchForKeyword(final String keyword, final PageRequest pageRequest)
            throws ParseException, JsonProcessingException;

}
