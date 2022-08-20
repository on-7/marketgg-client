package com.nhnacademy.marketgg.client.interceptor;

import com.nhnacademy.marketgg.client.annotation.Auth;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        log.info("Path = {}", request.getRequestURI());

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        Auth authAnnotation = method.getMethodAnnotation(Auth.class);

        if (Objects.isNull(authAnnotation)) {
            authAnnotation = method.getBean().getClass().getAnnotation(Auth.class);
            if (Objects.isNull(authAnnotation)) {
                // 클래스 또는 메서드에 @Auth 어노테이션이 없으면 인증되지 않은 회원도 접근 가능.
                return true;
            }
        }

        if (Objects.isNull(SecurityContextHolder.getContext())) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인 후 접근 가능합니다.");
            return false;
        }

        return true;
    }

}
