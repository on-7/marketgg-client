package com.nhnacademy.marketgg.client.repository.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupToAuth;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberSignupResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import com.nhnacademy.marketgg.client.exception.ClientException;
import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.util.Date;
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

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final RestTemplate restTemplate;

    @Override
    public MemberSignupResponse signup(final MemberSignupToAuth memberSignupToAuth) {
        log.info("signup: start");
        HttpHeaders headers = getHttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<MemberSignupToAuth> httpEntity = new HttpEntity<>(memberSignupToAuth, headers);

        // TODO: 리팩터링 필요 @김훈민
        ResponseEntity<CommonResult<MemberSignupResponse>> exchange =
            restTemplate.exchange(requestUrl + "/auth/v1/signup", HttpMethod.POST,
                httpEntity, new ParameterizedTypeReference<>() {
                });

        if (exchange.getStatusCode().is5xxServerError() || !exchange.hasBody()) {
            throw new ServerException();
        }

        if (exchange.getStatusCode().is4xxClientError()) {
            throw new ClientException("회원가입 실패!");
        }

        log.info("signup success: {}", exchange.getStatusCode());

        return Objects.requireNonNull(exchange.getBody()).getData();
    }

    @Override
    public EmailExistResponse checkEmail(final EmailRequest emailRequest) {
        log.info("checkEmail: start");
        HttpHeaders headers = getHttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<EmailRequest> httpEntity = new HttpEntity<>(emailRequest, headers);

        ResponseEntity<CommonResult<EmailExistResponse>> exchange =
            restTemplate.exchange(requestUrl + "/auth/v1/check/email", HttpMethod.POST, httpEntity,
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

    @Override
    public void withdraw(final MemberWithdrawRequest memberWithdrawRequest, final String sessionId) {
        log.info("withdraw: start");
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberWithdrawRequest> httpEntity = new HttpEntity<>(memberWithdrawRequest, httpHeaders);

        // TODO: 리팩터링 필요 @김훈민
        ResponseEntity<CommonResult<String>> response =
            restTemplate.exchange(requestUrl + "/auth/v1/members/info", HttpMethod.DELETE, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
        log.info("{}: {}", Objects.requireNonNull(response.getBody()).getData(), response.getStatusCode());
    }

    @Override
    public MemberUpdateToAuthResponse update(final MemberUpdateToAuth memberUpdateToAuth,
                                             final String sessionId) {
        log.info("update: start");
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // TODO: 리팩터링 필요 @김훈민
        HttpEntity<MemberUpdateToAuth> httpEntity =
            new HttpEntity<>(memberUpdateToAuth, httpHeaders);
        ResponseEntity<MemberUpdateToAuthResponse> response =
            restTemplate.exchange(requestUrl + "/auth/v1/members/info", HttpMethod.PUT, httpEntity,
                MemberUpdateToAuthResponse.class
            );

        HttpHeaders headers = response.getHeaders();

        List<String> jwtHeader = headers.get(AUTHORIZATION);
        List<String> expiredHeader = headers.get(JwtInfo.JWT_EXPIRE);

        if (Objects.isNull(jwtHeader) || Objects.isNull(expiredHeader)) {
            throw new LoginFailException();
        }

        String jwt = jwtHeader.get(0);
        String expire = expiredHeader.get(0);

        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);

        JwtInfo jwtInfo = new JwtInfo(jwt, expire);
        Date expireDate = jwtInfo.localDateTimeToDateForRenewToken(jwtInfo.getJwtExpireDate());

        log.info("login success: {}", jwtInfo.getJwt());

        redisTemplate.opsForHash().put(sessionId, JwtInfo.JWT_REDIS_KEY, jwtInfo);
        redisTemplate.expireAt(sessionId, expireDate);

        if (Objects.isNull(response.getBody())) {
            throw new ServerException();
        }

        return response.getBody();
    }

    private HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

}
