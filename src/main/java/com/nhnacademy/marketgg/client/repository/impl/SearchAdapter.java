package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestBody;
import com.nhnacademy.marketgg.client.dto.request.SearchToCategoryRequestBody;
import com.nhnacademy.marketgg.client.dto.request.searchutil.Bool;
import com.nhnacademy.marketgg.client.dto.request.searchutil.BoolQuery;
import com.nhnacademy.marketgg.client.dto.request.searchutil.Id;
import com.nhnacademy.marketgg.client.dto.request.searchutil.MultiMatch;
import com.nhnacademy.marketgg.client.dto.request.searchutil.Must;
import com.nhnacademy.marketgg.client.dto.request.searchutil.Query;
import com.nhnacademy.marketgg.client.dto.request.searchutil.Score;
import com.nhnacademy.marketgg.client.dto.request.searchutil.Sort;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchRepository;
import com.nhnacademy.marketgg.client.util.Converter;
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
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Converter converter = new Converter();

    private static final String DEFAULT_ELASTIC = "http://localhost:9200";
    private static final String DEFAULT_ELASTIC_PRODUCT = "/products/_search";

    @Override
    public List<SearchProductResponse> searchProductForCategory(final String code,
                                                                final SearchRequest request)
            throws ParseException, JsonProcessingException {

        HttpEntity<String> requestEntity =
                new HttpEntity<>(
                        objectMapper.writeValueAsString(
                                this.buildCategoryCodeRequestBody(code, request,
                                                                  converter.converter(
                                                                          request.getRequest()))),
                        this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        HttpEntity<String> requestEntity =
                new HttpEntity<>(
                        objectMapper.writeValueAsString(
                                this.buildKeywordRequestBody(request, converter.converter(
                                        request.getRequest()))), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    private ResponseEntity<String> doRequest(final HttpEntity<String> request) {
        return restTemplate.exchange(DEFAULT_ELASTIC + DEFAULT_ELASTIC_PRODUCT,
                                     HttpMethod.POST,
                                     request,
                                     String.class);
    }

    private SearchToCategoryRequestBody buildCategoryCodeRequestBody(final String code,
                                                                     final SearchRequest request,
                                                                     final String typo) {

        return SearchToCategoryRequestBody.builder()
                                          .sort(List.of(new Sort(new Score("desc"), new Id("asc"))))
                                          .from(request.getPageRequest().getPageNumber())
                                          .size(request.getPageRequest().getPageSize())
                                          .query(new BoolQuery(
                                                  new Bool(new Must(List.of(new MultiMatch(code,
                                                                                           List.of("categoryCode")),
                                                                            new MultiMatch(
                                                                                    request.getRequest() +
                                                                                            " " +
                                                                                            typo,
                                                                                    List.of("productName",
                                                                                            "productName_forEng",
                                                                                            "content",
                                                                                            "content_forEng")))))))
                                          .build();
    }

    private SearchRequestBody buildKeywordRequestBody(final SearchRequest request,
                                                      final String typo) {

        return SearchRequestBody.builder()
                                .sort(List.of(new Sort(new Score("desc"), new Id("asc"))))
                                .from(request.getPageRequest().getPageNumber())
                                .size(request.getPageRequest().getPageSize())
                                .query(new Query(new MultiMatch(request.getRequest() + " " + typo,
                                                                List.of("productName",
                                                                        "productName_forEng",
                                                                        "content",
                                                                        "content_forEng"))))
                                .build();
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
            list.add(objectMapper.readValue(body.toJSONString(), SearchProductResponse.class));
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
