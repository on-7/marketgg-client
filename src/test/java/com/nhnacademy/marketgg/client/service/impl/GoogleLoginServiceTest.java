package com.nhnacademy.marketgg.client.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import com.nhnacademy.marketgg.client.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.client.repository.auth.OauthRepository;
import com.nhnacademy.marketgg.client.service.auth.GoogleLoginService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class GoogleLoginServiceTest {

    @InjectMocks
    GoogleLoginService googleLoginService;

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    OauthRepository oauthRepository;

    @Mock
    AuthRepository authRepository;

    String userJwt =
        "header.eyJzdWIiOiJmNjRiYTQyNC1iZWY4LTQ2OTMtODQ5NS02ZTQwMzVlMGY1OTgiLCJBVVRIT1JJVElFUyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjU4OTc4OTEyLCJleHAiOjE2OTA1MzU4Mzh9.signature";


    @Test
    @DisplayName("Redirect Url 요청")
    void getRedirectUrl() {
        assertThat(googleLoginService.getRedirectUrl()).isNotNull();
    }

    @Test
    @DisplayName("사용자 프로필 요청")
    void tesGetToken() {

        String code = "code";
        String sessionId = "sessionId";
        String email = "email@gmail.com";
        String name = "홍길동";
        String provider = "GOOGLE";

        MemberInfo memberInfo = new MemberInfo("email", "name", "phoneNumber", "VIP", 'M', LocalDate.now(), 1L);
        CommonResult<MemberInfo> success = CommonResult.success(memberInfo);

        GoogleProfile mockGoogle = new GoogleProfile();
        ReflectionTestUtils.setField(mockGoogle, "email", email);
        ReflectionTestUtils.setField(mockGoogle, "name", name);
        ReflectionTestUtils.setField(mockGoogle, "provider", provider);

        CommonResult<GoogleProfile> profileResponse = CommonResult.success(mockGoogle);
        ResponseEntity<CommonResult<GoogleProfile>> response = ResponseEntity.status(UNAUTHORIZED)
                                                                             .contentType(APPLICATION_JSON)
                                                                             .body(profileResponse);

        given(oauthRepository.getProfile(any(), any())).willReturn(response);
        lenient().when(authRepository.getMemberInfo(any(HttpHeaders.class)))
                 .thenReturn(ResponseEntity.of(Optional.of(success)));

        Optional<GoogleProfile> token = googleLoginService.attemptLogin(code, sessionId);
        assertThat(token.orElse(new GoogleProfile()).getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("로그인 성공")
    void testLogin() {
        String code = "code";
        String sessionId = "sessionId";
        MemberInfo memberInfo = new MemberInfo("email", "name", "phoneNumber", "VIP", 'M', LocalDate.now(), 1L);
        CommonResult<MemberInfo> success = CommonResult.success(memberInfo);
        CommonResult<GoogleProfile> response = CommonResult.success(new GoogleProfile());

        ResponseEntity<CommonResult<GoogleProfile>> jwt = ResponseEntity.status(OK)
                                                                        .header(HttpHeaders.AUTHORIZATION,
                                                                            "Bearer " + userJwt)
                                                                        .header(JwtInfo.JWT_EXPIRE,
                                                                            LocalDateTime.now().toString())
                                                                        .body(response);

        given(authRepository.getMemberInfo(any(HttpHeaders.class))).willReturn(ResponseEntity.of(Optional.of(success)));
        given(oauthRepository.getProfile(any(), any())).willReturn(jwt);
        HashOperations<String, Object, Object> opsForHash = mock(HashOperations.class);
        given(redisTemplate.opsForHash()).willReturn(opsForHash);
        willDoNothing().given(opsForHash).put(anyString(), anyString(), any(JwtInfo.class));

        Optional<GoogleProfile> token = googleLoginService.attemptLogin(code, sessionId);
        assertThat(token).isNotPresent();
    }

}
