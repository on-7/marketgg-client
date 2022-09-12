package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.filter.OriginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * Web Configuration 을 설정할 수 있습니다.
 *
 * @author 윤동열, 박세완
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
     * @since 1.0.0
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public CommonsMultipartResolver filterMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(20_000_000);
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

    @Bean
    public FilterRegistrationBean<OriginFilter> originFilter() {
        FilterRegistrationBean<OriginFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new OriginFilter());
        registrationBean.setOrder(0);

        return registrationBean;
    }

}
