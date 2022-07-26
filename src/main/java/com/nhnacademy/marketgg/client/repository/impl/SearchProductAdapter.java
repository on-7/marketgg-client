package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.SearchProduct;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.SearchProductForCategory;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.SearchProductForPrice;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.SearchToCategoryForPrice;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.Sort;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.SortForPrice;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.sortdata.Id;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.sortdata.Price;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.sortdata.Score;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.Bool;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.BoolQuery;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.MultiMatch;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.Must;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.Query;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchProductRepository;
import com.nhnacademy.marketgg.client.util.Converter;
import java.util.ArrayList;
import java.util.List;
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

    private final JSONParser jsonParser = new JSONParser();
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Converter converter = new Converter();

    private static final String DEFAULT_ELASTIC = "http://133.186.153.181:9200";
    private static final String DEFAULT_ELASTIC_PRODUCT = "/products/_search";
    private static final List<String> CATEGORY_FIELD = List.of("categoryCode");
    private static final List<String> DEFAULT_FIELD =
            List.of("productName", "productName.forSyno", "content", "content.forSyno",
                    "description", "description.forSyno");

    private static final List<Sort> DEFAULT_SORT =
            List.of(new Sort(new Score("desc"), new Id("adc")));

    @Override
    public List<SearchProductResponse> searchProductForCategory(final String code,
                                                                final SearchRequest request)
            throws ParseException, JsonProcessingException {

        HttpEntity<String> requestEntity = new HttpEntity<>(
                objectMapper.writeValueAsString(
                        this.buildCategoryCodeRequestBody(code, request,
                                                          converter.converter(
                                                                  request.getRequest()), null)),
                this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException {

        HttpEntity<String> requestEntity = new HttpEntity<>(
                objectMapper.writeValueAsString(
                        this.buildKeywordRequestBody(request, converter.converter(
                                request.getRequest()), null)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductForCategorySortPrice(final String code,
                                                                         final SearchRequest request,
                                                                         final String type)
            throws JsonProcessingException, ParseException {

        HttpEntity<String> requestEntity = new HttpEntity<>(
                objectMapper.writeValueAsString(
                        this.buildCategoryCodeRequestBody(code, request, converter.converter(
                                request.getRequest()), type)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    @Override
    public List<SearchProductResponse> searchProductForKeywordSortPrice(final SearchRequest request,
                                                                        final String type)
            throws JsonProcessingException, ParseException {

        HttpEntity<String> requestEntity = new HttpEntity<>(
                objectMapper.writeValueAsString(
                        this.buildKeywordRequestBody(request, converter.converter(
                                request.getRequest()), type)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity).getBody());
    }

    private ResponseEntity<String> doRequest(final HttpEntity<String> request) {
        return restTemplate.exchange(DEFAULT_ELASTIC + DEFAULT_ELASTIC_PRODUCT,
                                     HttpMethod.POST,
                                     request,
                                     String.class);
    }

    private Object buildCategoryCodeRequestBody(final String code,
                                                final SearchRequest request,
                                                final String typo,
                                                final String type) {

        String requestString = request + " " + typo;

        if (Objects.isNull(type)) {
            return SearchProductForCategory.builder()
                                           .sort(DEFAULT_SORT)
                                           .from(request.getPageRequest().getPageNumber())
                                           .size(request.getPageRequest().getPageSize())
                                           .query(new BoolQuery(
                                                   new Bool(new Must(List.of(new MultiMatch(code,
                                                                                            CATEGORY_FIELD),
                                                                             new MultiMatch(
                                                                                     requestString,
                                                                                     DEFAULT_FIELD))))))
                                           .build();
        }
        return SearchToCategoryForPrice.builder()
                                       .sort(List.of(
                                               new SortForPrice(new Score("desc"), new Price(type),
                                                                new Id("asc"))))
                                       .from(request.getPageRequest().getPageNumber())
                                       .size(request.getPageRequest().getPageSize())
                                       .query(new BoolQuery(
                                               new Bool(new Must(List.of(new MultiMatch(code,
                                                                                        CATEGORY_FIELD),
                                                                         new MultiMatch(
                                                                                 requestString,
                                                                                 DEFAULT_FIELD))))))
                                       .build();
    }

    private Object buildKeywordRequestBody(final SearchRequest request,
                                           final String typo,
                                           final String type) {

        String requestString = request + " " + typo;

        if (Objects.isNull(type)) {
            return SearchProduct.builder()
                                .sort(DEFAULT_SORT)
                                .from(request.getPageRequest().getPageNumber())
                                .size(request.getPageRequest().getPageSize())
                                .query(new Query(
                                        new MultiMatch(requestString,
                                                       DEFAULT_FIELD)))
                                .build();
        }
        return SearchProductForPrice.builder()
                                    .sort(List.of(
                                            new SortForPrice(new Score("desc"), new Price(type),
                                                             new Id("asc"))))
                                    .from(request.getPageRequest().getPageNumber())
                                    .size(request.getPageRequest().getPageSize())
                                    .query(new Query(
                                            new MultiMatch(requestString,
                                                           DEFAULT_FIELD)))
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
