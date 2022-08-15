package com.nhnacademy.marketgg.client.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.exception.LogoutException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.repository.AuthRepository;

import java.time.LocalDateTime;
import java.util.Date;

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
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class DefaultAuthServiceTest {

    @InjectMocks
    DefaultAuthService authService;

    @Mock
    AuthRepository authRepository;

    @Spy
    PasswordEncoder passwordEncoder;

    @Mock
    RedisTemplate<String, JwtInfo> redisTemplate;

    @Test
    @DisplayName("로그인")
    void doLogin() {
        LoginRequest loginRequest = new LoginRequest("email@gmail.com", "password");
        String sessionId = "sessionId";
        JwtInfo jwtInfo = mock(JwtInfo.class);

        HashOperations<String, Object, Object> hashOps = mock(HashOperations.class);
        given(redisTemplate.opsForHash()).willReturn(hashOps);
        given(redisTemplate.expireAt(anyString(), any(Date.class))).willReturn(true);
        lenient().when(hashOps.get(sessionId, JwtInfo.JWT_REDIS_KEY)).thenReturn(jwtInfo);

        ResponseEntity<Void> resp = ResponseEntity.status(HttpStatus.OK)
                                                  .header(HttpHeaders.AUTHORIZATION, "Bearer jwt")
                                                  .header(JwtInfo.JWT_EXPIRE, LocalDateTime.now().toString())
                                                  .build();

        given(authRepository.doLogin(loginRequest, sessionId)).willReturn(resp);

        authService.doLogin(loginRequest, sessionId);

        then(redisTemplate).should(times(1)).opsForHash();
        then(redisTemplate).should(times(1)).expireAt(anyString(), any(Date.class));
        then(hashOps).should(times(1)).put(anyString(), anyString(), any(JwtInfo.class));

        then(passwordEncoder).should(times(1)).encode(anyString());
        then(authRepository).should(times(1)).doLogin(loginRequest, sessionId);
    }

    @Test
    @DisplayName("로그아웃")
    void logout() {
        String sessionId = "sessionId";
        JwtInfo jwtInfo = mock(JwtInfo.class);

        HashOperations<String, Object, Object> hashOps = mock(HashOperations.class);
        given(redisTemplate.opsForHash()).willReturn(hashOps);
        given(hashOps.get(sessionId, JwtInfo.JWT_REDIS_KEY)).willReturn(jwtInfo);
        given(hashOps.delete(sessionId, JwtInfo.JWT_REDIS_KEY)).willReturn(1L);

        ResponseEntity<Void> resp = ResponseEntity.status(HttpStatus.OK)
                                                  .build();
        given(authRepository.logout(anyString())).willReturn(resp);

        authService.logout(sessionId);

        then(authRepository).should(times(1)).logout(sessionId);
        then(redisTemplate).should(times(2)).opsForHash();
        then(hashOps).should(times(1)).delete(sessionId, JwtInfo.JWT_REDIS_KEY);
    }

    @Test
    @DisplayName("로그아웃2")
    void testLogout2() {
        String sessionId = "sessionId";

        HashOperations<String, Object, Object> hashOps = mock(HashOperations.class);
        given(redisTemplate.opsForHash()).willReturn(hashOps);
        given(hashOps.get(sessionId, JwtInfo.JWT_REDIS_KEY)).willReturn(null);

        authService.logout(sessionId);

        then(redisTemplate).should(times(1)).opsForHash();
        then(hashOps).should(times(1)).get(sessionId, JwtInfo.JWT_REDIS_KEY);
    }

    @Test
    @DisplayName("로그아웃 실패")
    void testLogoutFail() {
        String sessionId = "sessionId";
        JwtInfo jwtInfo = mock(JwtInfo.class);

        HashOperations<String, Object, Object> hashOps = mock(HashOperations.class);
        given(redisTemplate.opsForHash()).willReturn(hashOps);
        given(hashOps.get(sessionId, JwtInfo.JWT_REDIS_KEY)).willReturn(jwtInfo);

        ResponseEntity<Void> resp = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                  .build();
        given(authRepository.logout(anyString())).willReturn(resp);

        assertThatThrownBy(() -> authService.logout(sessionId))
                .isInstanceOf(LogoutException.class);
    }

}
