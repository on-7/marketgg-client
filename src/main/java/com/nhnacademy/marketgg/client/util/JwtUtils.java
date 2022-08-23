package com.nhnacademy.marketgg.client.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * JWT 관련 유틸리티 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    /**
     * 현재 세션 유지가 되고 있는 회원 정보의 JWT 정보를 반환합니다.
     * WebClient 등 HTTP 통신을 위한 인터페이스에서 토큰 기반 인증(Bearer Authentication) 등에 해당 메서드를 활용됩니다.
     *
     * @return 로그인 한 회원의 JWT
     */
    public static String getToken() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

}
