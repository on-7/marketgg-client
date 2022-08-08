package com.nhnacademy.marketgg.client.aspect;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * AOP 편의 메서드 모음입니다.
 *
 * @author 윤동열
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AspectUtils {

    /**
     * 요청 데이터를 반환 받습니다.
     *
     * @return 요청 정보
     */
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes requestAttributes =
            Objects.requireNonNull(
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        return requestAttributes.getRequest();
    }

}
