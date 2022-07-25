package com.nhnacademy.marketgg.client.aspect;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AspectUtils {

    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes requestAttributes =
            Objects.requireNonNull(
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

        return requestAttributes.getRequest();
    }

}
