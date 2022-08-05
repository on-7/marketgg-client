package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.repository.ImageRepository;
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
public class ImageAdapter implements ImageRepository {

    private final String gatewayIp;
    private static final String IMAGE_DEFAULT_URI = "/shop/v1/storage";
    private final RestTemplate restTemplate;

    @Override
    public ImageResponse retrieveImage(final Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ImageResponse> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ImageResponse> response =
            this.restTemplate.exchange(gatewayIp + IMAGE_DEFAULT_URI + "/" + id,
                                       HttpMethod.GET,
                                       httpEntity,
                                       new ParameterizedTypeReference<>() {
                                       });

        return response.getBody();
    }

    @Override
    public String downloadImage(String url) {
        return null;
    }

}
