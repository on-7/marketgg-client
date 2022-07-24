package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PageRequest;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface SearchRepository {

    List<SearchProductResponse> searchProductForCategory(final String categoryCode, final PageRequest pageRequest)
            throws JsonProcessingException, ParseException;

    List<SearchProductResponse> searchProductWithKeyword(final String keyword, final PageRequest pageRequest)
            throws JsonProcessingException, ParseException;

}
