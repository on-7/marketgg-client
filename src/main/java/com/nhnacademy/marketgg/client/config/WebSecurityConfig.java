package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 기본 설정을 진행합니다.
 *
 * @author 윤동열
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String OTO_CODE = "702";

    /**
     * 인증을 처리하는 여러 개의 SecurityFilter 를 담는 filter chain 입니다.
     *
     * @param http - 세부 보안 기능을 설정할 수 있는 API 제공 클래스
     * @return 인증 처리와 관련된 SecurityFilterChain
     * @throws Exception Spring Security 의 메소드에서 발생하는 예외입니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           RedisTemplate<String, Object> redisTemplate) throws Exception {

        http.csrf()
            .ignoringAntMatchers("/admin/products/**")
            .ignoringAntMatchers("/products/**")
            .ignoringAntMatchers("/editor/**");

        http.addFilterBefore(new AuthenticationFilter(redisTemplate),
            UsernamePasswordAuthenticationFilter.class);

        http.formLogin().disable()
            .logout().disable();

        http.authorizeRequests()
            .antMatchers("/", "/index", "/login", "/signup", "/products/**", "/editor/**").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            // TODO: 로그인이 필요한 경로 추가 해야합니다.
            .antMatchers("/cart/**", "/dibs/**", "/members/dibs/**", "/members/ggpass/**",
                "/customer-services/categories/" + OTO_CODE + "/**", "/orders/**", "/mygg/**", "/**/inquiries").authenticated()
            .anyRequest().permitAll();

        http.headers()
            .defaultsDisabled()
            .frameOptions().sameOrigin();

        http.exceptionHandling()
            .accessDeniedPage("/errors/403");

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                         .antMatchers("*.ico", "/plugins/**", "/images/**", "/css/**", "/js/**");
    }

}
