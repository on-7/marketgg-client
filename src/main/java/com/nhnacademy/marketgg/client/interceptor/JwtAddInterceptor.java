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

    /**
     * {@link ClientHttpRequestInterceptor} 인터페이스 추상 메서드를 재정의하여 서버로 요청을 보내기 전 토큰 정보가 있다면 주입 후 요청을 보냅니다.
     *
     * @param httpRequest - the request, containing method, URI, and headers
     * @param body        - the body of the request
     * @param execution   -  the request execution
     * @return 클라이언트 요청에 대한 응답 인터페이스 {@link ClientHttpResponse}
     * @throws IOException - {@link ClientHttpRequestExecution} execute 메서드 실행 도중 시 입출력 예외 발생 시 처리하기 위한 객체
     */
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

    /**
     * {@link HttpRequest} 로 받은 요청에 대해 토큰 기반 인증이 필요한지 검사합니다.
     *
     * @param uri - 토큰 기반 인증 검사가 필요한 요청(request) URI
     * @return 토큰 기반 인증이 필요한 URI 검사 여부
     */
    public boolean needJwt(String uri) {
        return !(uri.contains("login") || uri.contains("signup"));
    }

}
