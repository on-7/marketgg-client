package com.nhnacademy.marketgg.client.config;

import javax.servlet.ServletContext;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * Multipart-form Filter 를 추가한다.
 *
 * @author 윤동열
 * @see <a href="https://docs.spring.io/spring-security/site/docs/5.0.7.RELEASE/reference/html/csrf.html#csrf-multipartfilter">Spring security Multipart-form CSRF</a>
 */
public class SecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
    }

}