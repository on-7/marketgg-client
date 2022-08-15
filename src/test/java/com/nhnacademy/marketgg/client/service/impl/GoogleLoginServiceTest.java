package com.nhnacademy.marketgg.client.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import com.nhnacademy.marketgg.client.repository.OauthRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class GoogleLoginServiceTest {

    @InjectMocks
    GoogleLoginService googleLoginService;

    @Spy
    ObjectMapper objectMapper;

    @Mock
    RedisTemplate<String, JwtInfo> redisTemplate;

    @Mock
    OauthRepository oauthRepository;

    @Test
    @DisplayName("Redirect Url 요청")
    void getRedirectUrl() {
        assertThat(googleLoginService.getRedirectUrl()).isNotNull();
    }

    @Test
    @DisplayName("사용자 프로필 요청")
    void tesGetToken() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String code = "code";
        String sessionId = "sessionId";
        String email = "email@gmail.com";
        String name = "홍길동";
        String provider = "GOOGLE";

        GoogleProfile mockGoogle = new GoogleProfile();
        ReflectionTestUtils.setField(mockGoogle, "email", email);
        ReflectionTestUtils.setField(mockGoogle, "name", name);
        ReflectionTestUtils.setField(mockGoogle, "provider", provider);

        SingleResponse<GoogleProfile> response = new SingleResponse<>();
        ReflectionTestUtils.setField(response, "data", mockGoogle);
        String jsonResponse = mapper.writeValueAsString(response);

        given(oauthRepository.getProfile(any(), any())).willReturn(ResponseEntity.of(Optional.of(jsonResponse)));

        Optional<GoogleProfile> token = googleLoginService.getToken(code, sessionId);
        assertThat(token.orElse(new GoogleProfile()).getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("로그인 성공")
    void testLogin() throws Exception {
        String code = "code";
        String sessionId = "sessionId";
        ObjectMapper mapper = new ObjectMapper();
        SingleResponse<String> response = new SingleResponse<>();
        ReflectionTestUtils.setField(response, "data", "Login Success");

        String jsonResponse = mapper.writeValueAsString(response);
        ResponseEntity<String> jwt = ResponseEntity.status(HttpStatus.OK)
                                                   .header(HttpHeaders.AUTHORIZATION, "jwt")
                                                   .header(JwtInfo.JWT_EXPIRE,
                                                           LocalDateTime.now().toString())
                                                   .body(jsonResponse);

        given(oauthRepository.getProfile(any(), any())).willReturn(jwt);
        HashOperations<String, Object, Object> opsForHash = mock(HashOperations.class);
        given(redisTemplate.opsForHash()).willReturn(opsForHash);
        willDoNothing().given(opsForHash).put(anyString(), anyString(), any(JwtInfo.class));

        Optional<GoogleProfile> token = googleLoginService.getToken(code, sessionId);
        assertThat(token).isNotPresent();
    }

}
