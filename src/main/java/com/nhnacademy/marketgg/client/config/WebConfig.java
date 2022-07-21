package com.nhnacademy.marketgg.client.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Web Configuration 을 설정할 수 있습니다.
 *
 * @version 1.0.0
 */
@Configuration
public class WebConfig {

    /**
     * RestTemplate 을 원하는 값으로 설정 후 반환합니다.
     *
     * @param builder - RestTemplate 의 설정을 변경할 수 있는 Builder 객체입니다.
     * @return 원하는 값으로 설정한 RestTemplate 객체를 반환합니다.
     * @since 1.0.0
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setReadTimeout(Duration.ofSeconds(10L))
            .setConnectTimeout(Duration.ofSeconds(5L))
            .build();
    }

    /**
     * GATEWAY 의 IP 와 PORT 번호를 반환합니다.
     *
     * @param ip - GATEWAY 의 URI 입니다.
     * @return GATEWAY 의 IP 와 PORT 번호를 반환합니다.
     * @since 1.0.0
     */
    @Bean
    public String gatewayIp(@Value("${marketgg.gateway-origin}") String ip) {
        return ip;
    }

    /**
     * Html 의 Form 태그를 Get, Post 맵핑 이외릐 메소드로 ACTION 할 수 있게 해줍니다.
     *
     * @return HiddenHttpMethodFilter 객체를 반환합니다.
     * @since 1.0.0
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    // REVIEW 2:
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }

}
