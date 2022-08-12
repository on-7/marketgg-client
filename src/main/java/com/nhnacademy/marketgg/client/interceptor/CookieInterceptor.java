package com.nhnacademy.marketgg.client.interceptor;

import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Controller 메서드 실행 전 쿠키의 세션 ID 정보를 ThreadLocal 을 이용하여 저장하고 메서드 실행 완료 후 ThreadLocal 초기화.
 *
 * @author 윤동열
 */
public class CookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        SessionContext.set(this.getSessionId(request.getCookies()));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {

        SessionContext.remove();
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
