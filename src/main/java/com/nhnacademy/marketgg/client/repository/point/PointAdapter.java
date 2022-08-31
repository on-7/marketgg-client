package com.nhnacademy.marketgg.client.repository.point;

import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class PointAdapter implements PointRepository {

    private static final String BASE_URL = "http://localhost:7080/shop/v1/";

    private final RestTemplate restTemplate;

    @Override
    public List<PointRetrieveResponse> adminRetrievePointHistory() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<PointRetrieveResponse>> response =
                this.restTemplate.exchange(BASE_URL + "admin/points", HttpMethod.GET, request,
                                           new ParameterizedTypeReference<>() {
                                           });

        return response.getBody();
    }

    @Override
    public PointRetrieveResponse retrievePointHistory(final Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<PointRetrieveResponse> response =
                this.restTemplate.exchange(BASE_URL + "members/" + id + "/points", HttpMethod.GET, request,
                                           new ParameterizedTypeReference<>() {
                                           });

        return response.getBody();
    }
}

