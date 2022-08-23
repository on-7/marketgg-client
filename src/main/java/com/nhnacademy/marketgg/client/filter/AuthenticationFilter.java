package com.nhnacademy.marketgg.client.filter;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 인증 Principal 에 세션 아이디 및 자격 증명(Credential) 등 JWT 저장 관련 작업을 수행합니다.
 * {@link OncePerRequestFilter} 추상 클래스를 상속받아 사용자의 요청 당 한 번만 필터가 동작합니다.
 *
 * @author 윤동열
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        log.info("Auth Filter Path = {}", request.getRequestURI());

        try {
            String sessionId = getSessionId(request.getCookies());

            if (Objects.isNull(sessionId)) {
                filterChain.doFilter(request, response);
                return;
            }

            Optional<JwtInfo> opJwtInfo =
                Optional.ofNullable((JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY));

            if (opJwtInfo.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            JwtInfo jwtInfo = opJwtInfo.get();

            // Authentication Principal 에 SessionId, Credential 에 JWT 저장
            Authentication authentication =
                new UsernamePasswordAuthenticationToken(sessionId, jwtInfo.getJwt(),
                                                        jwtInfo.getAuthorities()
                                                               .stream()
                                                               .map(SimpleGrantedAuthority::new)
                                                               .collect(toList()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            log.info("Security Context Remove");
            SecurityContextHolder.clearContext();
        }

    }

    private String getSessionId(Cookie[] cookies) {

        if (Objects.isNull(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (Objects.equals(JwtInfo.SESSION_ID, cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

}
