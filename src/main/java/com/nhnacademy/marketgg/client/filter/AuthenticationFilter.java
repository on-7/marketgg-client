package com.nhnacademy.marketgg.client.filter;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.jwt.Payload;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

            Authentication authentication =
                new UsernamePasswordAuthenticationToken(jwtInfo.getJwt(), "",
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
