package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequestBody;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequestBodyForBool;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchRepository;
import com.nhnacademy.marketgg.client.utils.KoreanToEnglishTranslator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SearchAdapter implements SearchRepository {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String gatewayIp;
    private final KoreanToEnglishTranslator converter;
    private final JSONParser jsonParser;
    private static final String DEFAULT_ELASTIC_PRODUCT = "/elastic/products/_search";
    private static final String DEFAULT_ELASTIC_BOARD = "/elastic/boards/_search";
    private static final String PRODUCT = "product";
    private static final String BOARD = "board";
    private static final String CATEGORY = "category";
    private static final String REASON = "reason";
    private static final String STATUS = "status";

    @Override
    public List<SearchProductResponse> searchProductForCategory(final String code,
                                                                final SearchRequest request,
                                                                final String type)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildSort(type);
        request.setRequest(request.getRequest() + " " + converter.converter(request.getRequest()));
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBodyForBool<>(code, sort, request, PRODUCT + CATEGORY)),
                                 this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, PRODUCT).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request,
                                                                final String type)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildSort(type);
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBody<>(sort, request, PRODUCT)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, PRODUCT).getBody());
    }

    @Override
    public List<SearchBoardResponse> searchBoardWithCategory(final String code,
                                                             final SearchRequest request)
            throws JsonProcessingException, ParseException {

        return getSearchBoardResponses(code, request, CATEGORY);
    }

    @Override
    public List<SearchBoardResponse> searchBoardWithKeyword(final SearchRequest request)
            throws JsonProcessingException, ParseException {

        Map<String, String> sort = this.buildSort(null);
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBody<>(sort, request, BOARD)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, BOARD).getBody());
    }

    @Override
    public List<SearchBoardResponse> searchBoardWithReason(final String reason,
                                                           final SearchRequest request)
            throws JsonProcessingException, ParseException {

        return getSearchBoardResponses(reason, request, REASON);
    }

    @Override
    public List<SearchBoardResponse> searchBoardWithStatus(final String status,
                                                           final SearchRequest request)
            throws JsonProcessingException, ParseException {

        return getSearchBoardResponses(status, request, STATUS);
    }

    private List<SearchBoardResponse> getSearchBoardResponses(final String status, final SearchRequest request,
                                                              final String option)
            throws JsonProcessingException, ParseException {
        Map<String, String> sort = this.buildSort(null);
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBodyForBool<>(status, sort, request, BOARD + option)),
                                 this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, BOARD).getBody());
    }

    private Map<String, String> buildSort(final String type) {
        Map<String, String> sortMap = new LinkedHashMap<>();

        sortMap.put("_score", "desc");
        if (Objects.nonNull(type)) {
            sortMap.put("price", type);
        }
        sortMap.put("_id", "asc");

        return sortMap;
    }

    private ResponseEntity<String> doRequest(final HttpEntity<String> request,
                                             final String document) {
        String requestUri = DEFAULT_ELASTIC_PRODUCT;
        if (document.compareTo(PRODUCT) != 0) {
            requestUri = DEFAULT_ELASTIC_BOARD;
        }
        return restTemplate.exchange(gatewayIp + requestUri,
                                     HttpMethod.POST,
                                     request,
                                     String.class);
    }

    private <T> List<T> parsingResponseBody(final String response)
            throws ParseException, JsonProcessingException {

        List<T> list = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hitBody = (JSONArray) hits.get("hits");
        for (Object data : hitBody) {
            JSONObject source = (JSONObject) data;
            JSONObject body = (JSONObject) source.get("_source");
            list.add(objectMapper.readValue(body.toJSONString(), new TypeReference<>() {
            }));
        }

        return list;
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
