package com.nhnacademy.marketgg.client.interceptor;

import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * RestTemplate 을 이용한 요청 시 자동으로 헤더에 JWT 를 추가하기 위한 클래스입니다.
 *
 * @author 윤동열
 * @version 1.0.0
 * @see <a href="https://www.baeldung.com/spring-rest-template-interceptor">RestTemplate Interceptor</a>
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAddInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        log.info("path = {}", httpRequest.getURI().getPath());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!this.needJwt(httpRequest.getURI().getPath()) || Objects.isNull(authentication)) {
            return execution.execute(httpRequest, body);
        }

        log.info("RestTemplate Interceptor");
        httpRequest.getHeaders().setBearerAuth((String) authentication.getCredentials());

        return execution.execute(httpRequest, body);
    }

    public boolean needJwt(String uri) {
        return !(uri.contains("login") || uri.contains("signup"));
    }

}