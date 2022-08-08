package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.repository.ImageRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public String uploadImage(final MultipartFile image) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity =
            getLinkedMultiValueMapHttpEntity(image);


        ResponseEntity<ImageResponse> response =
            this.restTemplate.exchange(gatewayIp + IMAGE_DEFAULT_URI,
                                       HttpMethod.POST,
                                       httpEntity,
                                       new ParameterizedTypeReference<>() {
                                       });

        return response.getBody().getImageAddress() + response.getBody().getName();
    }

    private <T> HttpEntity<LinkedMultiValueMap<String, Object>> getLinkedMultiValueMapHttpEntity(
        MultipartFile image) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition",
                      "form-data; name=image; filename=" + image.getOriginalFilename());
        headerMap.add("Content-type", "application/octet-stream");
        HttpEntity<byte[]> imageBytes = new HttpEntity<>(image.getBytes(), headerMap);

        LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
        multipartReqMap.add("image", imageBytes);

        return new HttpEntity<>(multipartReqMap, headers);
    }

}
