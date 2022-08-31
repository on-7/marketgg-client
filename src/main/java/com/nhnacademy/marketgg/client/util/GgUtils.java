package com.nhnacademy.marketgg.client.util;

import com.nhnacademy.marketgg.client.jwt.Role;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 마이크로서비스에 API 요청을 보낼 때 필요한 URL 정보를 제공하는 인터페이스입니다.
 *
 * @author 윤동열
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GgUtils {

    public static final int WEEK_SECOND = 60 * 60 * 24 * 7;

    public static final String SHOP_SERVICE_PREFIX_V1 = "/shop/v1";
    public static final String AUTH_SERVICE_PREFIX_V1 = "/auth/v1";

    public static final String REDIRECT_TO_INDEX = "redirect:/";

    public static boolean hasRole(Authentication authentication, Role role) {
        return Objects.nonNull(authentication)
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority(role.name()));
    }

}
