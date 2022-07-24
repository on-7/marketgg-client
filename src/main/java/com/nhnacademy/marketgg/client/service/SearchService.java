package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface SearchService {

    List<SearchProductResponse> searchForCategory(final SearchRequest request)
            throws ParseException, JsonProcessingException;

    List<SearchProductResponse> searchForKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException;

}
