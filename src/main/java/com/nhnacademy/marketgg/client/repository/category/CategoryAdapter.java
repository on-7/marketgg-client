package com.nhnacademy.marketgg.client.repository.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.category.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.util.ResponseUtils;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
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
public class CategoryAdapter implements CategoryRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_ADMIN_CATEGORY = "/shop/v1/admin/categories";
    private static final String DEFAULT_CATEGORY = "/shop/v1/categories";
    private static final String DEFAULT_CATEGORY_Categorization = "/shop/v1/categories/categorizations/";

    @Override
    public void createCategory(final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        String request = objectMapper.writeValueAsString(categoryRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<CommonResult<Void>> response = restTemplate.exchange(gateWayIp + DEFAULT_ADMIN_CATEGORY,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
    }

    @Override
    public CategoryRetrieveResponse retrieveCategory(String id) throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<CategoryRetrieveResponse>>
                response = restTemplate.exchange(gateWayIp + DEFAULT_ADMIN_CATEGORY + "/" + id,
                HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<List<CategoryRetrieveResponse>>>
                response = restTemplate.exchange(gateWayIp + DEFAULT_CATEGORY,
                HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategoriesOnlyProducts() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<List<CategoryRetrieveResponse>>>
                response = restTemplate.exchange(gateWayIp + DEFAULT_CATEGORY_Categorization + "100",
                HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public List<CategorizationRetrieveResponse> retrieveCategorizations()
            throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<List<CategorizationRetrieveResponse>>>
                response = restTemplate.exchange(gateWayIp + "/shop/v1/admin/categorizations",
                HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String request = objectMapper.writeValueAsString(categoryRequest);

        ResponseEntity<CommonResult<Void>> response =
                restTemplate.exchange(gateWayIp + DEFAULT_ADMIN_CATEGORY + "/" + id,
                        HttpMethod.PUT,
                        new HttpEntity<>(request, this.buildHeaders()),
                        new ParameterizedTypeReference<>() {
                        });

        this.checkResponse(response);
    }

    @Override
    public void deleteCategory(final String id) throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<Void>> response =
                restTemplate.exchange(gateWayIp + DEFAULT_ADMIN_CATEGORY + "/" + id,
                        HttpMethod.DELETE,
                        requestEntity,
                        new ParameterizedTypeReference<>() {
                        });

        this.checkResponse(response);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    private <T> void checkResponse(final ResponseEntity<CommonResult<T>> response)
            throws UnAuthenticException, UnAuthorizationException {

        ResponseUtils.checkError(response);
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
