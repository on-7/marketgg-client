package com.nhnacademy.marketgg.server.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.annotation.Auth;
import com.nhnacademy.marketgg.server.aop.AspectUtils;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

        ObjectMapper mapper = new ObjectMapper();

        String uuidHeader = request.getHeader(AspectUtils.AUTH_ID);
        String rolesHeader = request.getHeader(AspectUtils.WWW_AUTHENTICATE);

        if (Objects.isNull(uuidHeader) || Objects.isNull(rolesHeader)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        List<String> roles = mapper.readValue(request.getHeader(AspectUtils.WWW_AUTHENTICATE),
            new TypeReference<>() {
            });

        if (roles.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        return true;
    }

}
