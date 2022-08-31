package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.dto.member.EmailRequest;
import com.nhnacademy.marketgg.client.dto.member.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.member.EmailUseResponse;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
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
public class MemberInfoAdapter implements MemberInfoRepository {

    @Value("${gg.gateway-origin}")
    private String requestUrl;

    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;

    @Override
    public EmailExistResponse checkEmail(final EmailRequest emailRequest) {
        log.info("checkEmail: start");
        HttpHeaders headers = getHttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<EmailRequest> httpEntity = new HttpEntity<>(emailRequest, headers);

        ResponseEntity<CommonResult<EmailExistResponse>> exchange =
            restTemplate.exchange(requestUrl + "/auth/v1/members/check/email", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        log.info("email exist {}", exchange.getBody());
        return Objects.requireNonNull(exchange.getBody()).getData();
    }

    @Override
    public EmailUseResponse useEmail(final EmailRequest emailRequest) {
        log.info("checkEmail: start");
        HttpHeaders headers = getHttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<EmailRequest> httpEntity = new HttpEntity<>(emailRequest, headers);

        ResponseEntity<CommonResult<EmailUseResponse>> exchange =
            restTemplate.exchange(requestUrl + "/auth/v1/members/use/email", HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        log.info("email exist {}", exchange.getBody());

        return Objects.requireNonNull(exchange.getBody()).getData();
    }

    private HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

}
