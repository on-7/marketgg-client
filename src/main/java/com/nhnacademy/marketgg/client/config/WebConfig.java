package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.interceptor.JwtAddInterceptor;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Web Configuration 을 설정할 수 있습니다.
 *
 * @author 윤동열, 이제훈
 * @version 1.0.0
 */
@Slf4j
@Configuration
public class WebConfig {

    /**
     * GATEWAY 의 IP 와 PORT 번호를 반환합니다.
     *
     * @param ip - GATEWAY 의 URI 입니다.
     * @return GATEWAY 의 IP 와 PORT 번호를 반환합니다.
     * @author 박세완
     * @since 1.0.0
     */
    @Bean
    public String gatewayIp(@Value("${gg.gateway-origin}") String ip) {
        return ip;
    }

    /**
     * Html 의 Form 태그를 Get, Post 맵핑 이외의 메소드로 ACTION 할 수 있게 해줍니다.
     *
     * @return HiddenHttpMethodFilter 객체를 반환합니다.
     * @author 박세완
     * @since 1.0.0
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }



    @Bean
    public CommonsMultipartResolver filterMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(1_000_000);
        return resolver;
    }

    @Bean
    public FilterRegistrationBean<MultipartFilter> multipartFilter() {
        FilterRegistrationBean<MultipartFilter> registrationBean
            = new FilterRegistrationBean<>();

        registrationBean.setFilter(new MultipartFilter());
        registrationBean.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER - 1);

        return registrationBean;
    }

    /**
     * 사용자 언어 환경을 설정해주기 위한 Resolver 입니다.
     *
     * @return 사용자 언어환경을 설정해주고 초기값을 지닌 SessionLocalResolver 를 반환합니다.
     * @author 박세완
     * @since 1.0.0
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);

        return sessionLocaleResolver;
    }

}
