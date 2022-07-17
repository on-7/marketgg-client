package com.nhnacademy.marketgg.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableWebSecurity
public class WebSecurityConfig {

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
    //
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     // FIXME: th:action 적용 후 주석 해제
    //     // http.csrf().and()
    //     //     .sessionManagement().disable();
    //
    //     http.authorizeRequests()
    //         .antMatchers("/**").permitAll();
    //
    //     // TODO: 경로에 따른 접근 설정
    //     http.headers()
    //         .defaultsDisabled()
    //         .frameOptions().sameOrigin();
    //
    //     return http.build();
    // }
    //
    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return web -> web.ignoring()
    //                      .antMatchers("/resources/**");
    // }

}
