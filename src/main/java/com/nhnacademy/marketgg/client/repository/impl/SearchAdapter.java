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
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchAdapter implements SearchRepository {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final KoreanToEnglishTranslator koreanTranslator;
    private final JSONParser jsonParser;
    private static final String DEFAULT_ELASTIC_PRODUCT = "/products/_search";
    private static final String DEFAULT_ELASTIC_BOARD =  "/boards/_search";
    private static final String PRODUCT = "product";
    private static final String BOARD = "board";

    @Override
    public List<SearchProductResponse> searchProductForCategory(final String optionCode,
                                                                final SearchRequest request,
                                                                final String priceSortType)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildSort(priceSortType);
        request.setRequest(request.getRequest() + " " + koreanTranslator.converter(request.getRequest()));
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBodyForBool<>(optionCode, sort, request, PRODUCT)),
                                 this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, PRODUCT).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request,
                                                                final String priceSortType)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildSort(priceSortType);
        request.setRequest(request.getRequest() + " " + koreanTranslator.converter(request.getRequest()));
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBody<>(sort, request)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, PRODUCT).getBody());
    }

    @Override
    public List<SearchBoardResponse> searchBoardWithCategoryCode(final String categoryCode,
                                                                 final SearchRequest request,
                                                                 final String option)
            throws JsonProcessingException, ParseException {

        Map<String, String> sort = this.buildSort(null);
        request.setRequest(request.getRequest() + " " + koreanTranslator.converter(request.getRequest()));
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBodyForBool<>(categoryCode, sort, request, BOARD)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, BOARD).getBody());
    }

    @Override
    public List<SearchBoardResponse> searchBoardWithOption(final String categoryCode,
                                                           final String optionCode,
                                                           final SearchRequest request,
                                                           final String option)
            throws JsonProcessingException, ParseException {

        Map<String, String> sort = this.buildSort(null);
        request.setRequest(
                request.getRequest() + " " + koreanTranslator.converter(request.getRequest()));
        HttpEntity<String> requestEntity =
                new HttpEntity<>(objectMapper.writeValueAsString(
                        new SearchRequestBodyForBool<>(categoryCode, sort, request, optionCode, option)),
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
        log.error(request.toString());
        String requestUri = DEFAULT_ELASTIC_PRODUCT;
        if (document.compareTo(PRODUCT) != 0) {
            requestUri = DEFAULT_ELASTIC_BOARD;
        }
        // FIXME: data 파일 연결해서 사용 (path 제대로 잡기)
        return restTemplate.exchange("http://133.186.153.181:9200" + requestUri,
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
