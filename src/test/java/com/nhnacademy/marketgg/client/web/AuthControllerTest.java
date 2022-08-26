package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AuthController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                      .alwaysDo(print())
                                      .build();
    }

    @Test
    @DisplayName("로그인 페이지")
    void login() throws Exception {

        this.mockMvc.perform(get("/login"))
                    .andExpect(view().name("pages/members/login"));
    }

    @Test
    @DisplayName("로그인 진행")
    void doLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("email@gmail.com", "password");

        willDoNothing().given(authService).doLogin(any(LoginRequest.class), anyString());

        this.mockMvc.perform(post("/login")
                                     .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                     .param("email", loginRequest.getEmail())
                                     .param("password", loginRequest.getPassword()))
                    .andExpect(cookie().exists(JwtInfo.SESSION_ID))
                    .andExpect(status().is3xxRedirection());

        then(authService).should(times(1)).doLogin(any(LoginRequest.class), anyString());
    }

    @Test
    @DisplayName("로그아웃")
    void logout() throws Exception {
        willDoNothing().given(authService).logout();

        this.mockMvc.perform(get("/logout"))
                    .andExpect(status().is3xxRedirection());
    }

}
