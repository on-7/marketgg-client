package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchRepository;
import java.util.ArrayList;
import java.util.List;
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

    private final JSONParser jsonParser = new JSONParser();
    private final String elasticIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_ELASTIC_PRODUCT = "/products/_search";

    @Override
    public List<SearchProductResponse> searchProductForCategory(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        HttpEntity<String> requestEntity =
                new HttpEntity<>(this.buildCategoryCodeRequestBody(request),this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        HttpEntity<String> requestEntity =
                new HttpEntity<>(this.buildKeywordRequestBody(request), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    private ResponseEntity<String> doRequest(final HttpEntity<String> request) {
        return restTemplate.exchange(elasticIp + DEFAULT_ELASTIC_PRODUCT,
                                     HttpMethod.POST,
                                     request,
                                     String.class);
    }

    private String buildCategoryCodeRequestBody(final SearchRequest request) {
        return "{\n" +
                "    \"sort\": [\n" +
                "        {\n" +
                "            \"id\": {\n" +
                "                \"order\": \"asc\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"from\": " + request.getPageRequest().getPageNumber() + ",\n" +
                "    \"size\": " + request.getPageRequest().getPageSize() + ",\n" +
                "    \"query\": {\n" +
                "        \"bool\": {\n" +
                "            \"should\": [\n" +
                "                    {\n" +
                "                    \"match\": {\n" +
                "                        \"categoryCode\": \"" + request.getRequest() + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    private String buildKeywordRequestBody(final SearchRequest request) {
        return "{\n" +
                "    \"sort\": [\n" +
                "        {\n" +
                "            \"id\": {\n" +
                "                \"order\": \"asc\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"from\": " + request.getPageRequest().getPageNumber() + ",\n" +
                "    \"size\": " + request.getPageRequest().getPageSize() + ",\n" +
                "    \"query\": {\n" +
                "        \"bool\": {\n" +
                "            \"should\": [\n" +
                "                    {\n" +
                "                    \"match\": {\n" +
                "                        \"content\": \"" + request.getRequest() + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                    {\n" +
                "                    \"match_phrase\": {\n" +
                "                        \"content.forEng\": \"" + request.getRequest() + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    private List<SearchProductResponse> parsingResponseBody(final String response)
            throws ParseException, JsonProcessingException {

        List<SearchProductResponse> list = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hitBody = (JSONArray) hits.get("hits");

        for (Object data : hitBody) {
            JSONObject source = (JSONObject) data;
            JSONObject body = (JSONObject) source.get("_source");
            list.add(new SearchProductResponse(objectMapper.readValue(body.toJSONString(), SearchProductResponse.class)));
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
