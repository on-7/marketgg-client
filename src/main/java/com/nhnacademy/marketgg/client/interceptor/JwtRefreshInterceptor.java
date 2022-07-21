package com.nhnacademy.marketgg.client.interceptor;

import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
public class JwtRefreshInterceptor implements ClientHttpRequestInterceptor {

    private final RedisTemplate<String, JwtInfo> redisTemplate;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        ServletRequestAttributes requestAttributes
            = Objects.requireNonNull(
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        Object session
            = requestAttributes.getRequest().getSession(true).getAttribute(JwtInfo.SESSION_ID);

        if (Objects.isNull(session)) {
            return execution.execute(httpRequest, body);
        }

        String sessionId = (String) session;

        JwtInfo jwtInfo = (JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_KEY);

        if (Objects.isNull(jwtInfo)) {
            return execution.execute(httpRequest, body);
        }

        log.info("RestTemplate Interceptor");
        httpRequest.getHeaders().setBearerAuth(jwtInfo.getJwt());

        return execution.execute(httpRequest, body);
    }

}
