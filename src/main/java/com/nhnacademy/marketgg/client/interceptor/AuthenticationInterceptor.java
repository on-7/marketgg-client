package com.nhnacademy.marketgg.client.interceptor;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.jwt.Payload;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final ObjectMapper mapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        log.info("Path = {}", request.getRequestURI());

        String sessionId = Optional.ofNullable((String) request.getSession().getAttribute(JwtInfo.SESSION_ID))
                                   .orElseThrow(UnAuthenticException::new);

        Optional<JwtInfo> jwtInfo =
            Optional.ofNullable((JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY));

        if (jwtInfo.isPresent()) {
            String[] jwt = jwtInfo.get().getJwt().split("\\.");
            // JWT 는 header, payload, signature 가 . 으로 연결되어있다. (header.payload.signature)
            String jwtPayload = jwt[1];

            byte[] decode = Base64.getDecoder().decode(jwtPayload);
            Payload payload = mapper.readValue(new String(decode, StandardCharsets.UTF_8), Payload.class);

            log.info("role = {}", payload.getAuthorities().toString());

            Authentication authentication =
                new UsernamePasswordAuthenticationToken(payload.getSub(), "", payload.getAuthorities()
                                                                                     .stream()
                                                                                     .map(SimpleGrantedAuthority::new)
                                                                                     .collect(toList()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {

        SecurityContextHolder.clearContext();
    }
}
