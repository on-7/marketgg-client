package com.nhnacademy.marketgg.client.filter;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OriginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");

        if (Objects.nonNull(xForwardedFor)) {
            log.info("Origin: {}", xForwardedFor);
            log.info("URI: {}", httpServletRequest.getRequestURI());
        }

        chain.doFilter(request, response);
    }

}
