package com.nhnacademy.marketgg.client.repository.label;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.label.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.label.LabelRetrieveResponse;
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
public class LabelAdapter implements LabelRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_LABEL = "/shop/v1/admin/labels";

    @Override
    public void createLabel(final LabelRegisterRequest labelRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        String request = objectMapper.writeValueAsString(labelRequest);

        ResponseEntity<CommonResult<Void>> response = restTemplate.exchange(gateWayIp + DEFAULT_LABEL,
                                                                            HttpMethod.POST,
                                                                            new HttpEntity<>(request,
                                                                                             this.buildHeaders()),
                                                                            new ParameterizedTypeReference<>() {
                                                                            });

        this.checkResponseUri(response);
    }

    @Override
    public List<LabelRetrieveResponse> retrieveResponse() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<List<LabelRetrieveResponse>>> response =
                restTemplate.exchange(gateWayIp + DEFAULT_LABEL,
                                      HttpMethod.GET,
                                      requestEntity,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void deleteLabel(final Long id) throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<Void>> response = restTemplate.exchange(gateWayIp + DEFAULT_LABEL + "/" + id,
                                                                            HttpMethod.DELETE,
                                                                            requestEntity,
                                                                            new ParameterizedTypeReference<>() {
                                                                            });

        this.checkResponseUri(response);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    private <T> void checkResponseUri(final ResponseEntity<CommonResult<T>> response)
            throws UnAuthenticException, UnAuthorizationException {

        ResponseUtils.checkError(response);
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
