package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.service.LabelService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DefaultLabelService implements LabelService {

    private final String gateWayIp;
    private final HttpHeaders headers = buildHeaders();
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void createLabel(final LabelRegisterRequest labelRequest) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(labelRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);
        restTemplate.postForEntity(gateWayIp + "/admin/v1/labels",
                                   requestEntity,
                                   Void.class);
    }

    @Override
    public void deleteLabel(Long labelId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(gateWayIp + "/admin/v1/labels/" + labelId,
                              HttpMethod.DELETE,
                              requestEntity,
                              Void.class);
    }

    @Override
    public List<LabelRetrieveResponse> retrieveLabels() {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<LabelRetrieveResponse>> response = restTemplate.exchange(gateWayIp + "/admin/v1/labels",
                                                                                     HttpMethod.GET,
                                                                                     requestEntity,
                                                                                     new ParameterizedTypeReference<>() {
                                                                                     });
        return response.getBody();
    }

    private static HttpHeaders buildHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
