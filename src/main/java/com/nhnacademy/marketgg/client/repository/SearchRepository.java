package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface SearchRepository {

    List<SearchProductResponse> searchProductForCategory(final SearchRequest request)
            throws JsonProcessingException, ParseException;

    List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request)
            throws JsonProcessingException, ParseException;

}
