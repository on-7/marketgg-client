package com.nhnacademy.marketgg.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * Spring Security 기본 설정을 진행합니다.
 *
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Blowfish 알고리즘을 기반으로 비밀번호를 암호화합니다.
     *
     * @return 암호화 가능한 단방향 해시 함수인 BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증을 처리하는 여러 개의 SecurityFilter 를 담는 filter chain 입니다.
     *
     * @param http - 세부 보안 기능을 설정할 수 있는 API 제공 클래스
     * @return 인증 처리와 관련된 SecurityFilterChain
     * @throws Exception Spring Security 의 메소드에서 발생하는 예외입니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //FIXME Dave가 해결했는데.. Toast UI Image 업로드 과정에서 또 에러 발생.. 나중에 해결해보겠음
        http.csrf()
            .disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().disable()
            .logout().disable();

        http.authorizeRequests()
            .antMatchers("/**").permitAll();

        http.headers()
            .defaultsDisabled()
            .frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                         .antMatchers("/resources/**");
    }

}
