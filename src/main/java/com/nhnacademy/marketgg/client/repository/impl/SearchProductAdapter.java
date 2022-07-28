package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.SearchProduct;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.SearchProductForCategory;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchProductRepository;
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
public class SearchProductAdapter implements SearchProductRepository {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String gatewayIp;
    private final KoreanToEnglishTranslator converter;
    private static final String DEFAULT_ELASTIC_PRODUCT = "/elastic/products/_search";

    @Override
    public List<SearchProductResponse> searchProductForCategory(final String code,
                                                                final SearchRequest request,
                                                                final String type)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildProductSort(type);
        request.setRequest(request.getRequest() + " " + converter.converter(request.getRequest()));

        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchProductForCategory<>(code, sort, request)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request,
                                                                final String type)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildProductSort(type);

        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchProduct<>(sort, request)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    private Map<String, String> buildProductSort(String type) {
        Map<String, String> sortMap = new LinkedHashMap<>();
        sortMap.put("_score", "desc");
        if (Objects.nonNull(type)) {
            sortMap.put("price", type);
        }
        sortMap.put("_id", "asc");
        return sortMap;
    }

    private ResponseEntity<String> doRequest(final HttpEntity<String> request) {
        return restTemplate.exchange(gatewayIp + DEFAULT_ELASTIC_PRODUCT,
                                     HttpMethod.POST,
                                     request,
                                     String.class);
    }

    private List<SearchProductResponse> parsingResponseBody(final String response)
            throws ParseException, JsonProcessingException {

        JSONParser jsonParser = new JSONParser();
        List<SearchProductResponse> list = new ArrayList<>();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hitBody = (JSONArray) hits.get("hits");

        for (Object data : hitBody) {
            JSONObject source = (JSONObject) data;
            JSONObject body = (JSONObject) source.get("_source");
            list.add(objectMapper.readValue(body.toJSONString(),
                                            SearchProductResponse.class));
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
