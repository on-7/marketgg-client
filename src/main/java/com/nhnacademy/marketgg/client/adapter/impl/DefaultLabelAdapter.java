package com.nhnacademy.marketgg.client.adapter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.adapter.LabelAdapter;
import com.nhnacademy.marketgg.client.dto.request.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.response.LabelRetrieveResponse;
import java.util.List;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultLabelAdapter implements LabelAdapter {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void createLabel(final LabelRegisterRequest labelRequest)
            throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(labelRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(gateWayIp + "/admin/v1/labels",
                                                              HttpMethod.POST,
                                                              requestEntity,
                                                              Void.class);

        checkResponseUri(response);
    }

    @Override
    public List<LabelRetrieveResponse> retrieveResponse() {
        HttpEntity<String> requestEntity = new HttpEntity<>(buildHeaders());
        ResponseEntity<List<LabelRetrieveResponse>>
                response = restTemplate.exchange(gateWayIp + "/admin/v1/labels",
                                                 HttpMethod.GET,
                                                 requestEntity,
                                                 new ParameterizedTypeReference<>() {
                                                 });

        checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void deleteLabel(final Long id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(gateWayIp + "/admin/v1/labels/" + id,
                                                              HttpMethod.DELETE,
                                                              requestEntity,
                                                              Void.class);

        checkResponseUri(response);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    private <T> void checkResponseUri(final ResponseEntity<T> response) {
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
