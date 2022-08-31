package com.nhnacademy.marketgg.client.service.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import com.nhnacademy.marketgg.client.oauth2.TokenRequest;
import com.nhnacademy.marketgg.client.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.client.repository.auth.OauthRepository;
import com.nhnacademy.marketgg.client.service.auth.Oauth2Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 구글 로그인을 처리합니다.
 *
 * @author 윤동열
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleLoginService implements Oauth2Service {

    @Value("${gg.google.client-id}")
    private String clientId;

    @Value("${gg.google.client-key}")
    private String clientSecret;

    @Value("${gg.google.redirect-uri}")
    private String redirectUri;

    @Value("${gg.google.login-request-url}")
    private String loginRequestUrl;

    private final RedisTemplate<String, Object> redisTemplate;
    private final OauthRepository oauthRepository;
    private final AuthRepository authRepository;

    @Override
    public String getRedirectUrl() {
        String scope = "https://www.googleapis.com/auth/userinfo.profile "
            + "https://www.googleapis.com/auth/userinfo.email ";

        return "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + clientId
            + "&redirect_uri=" + redirectUri + "&scope=" + scope + "&response_type=code";
    }

    @Override
    public Optional<GoogleProfile> attemptLogin(final String code, final String sessionId) {
        TokenRequest parameters = new TokenRequest(clientId, clientSecret, code);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<CommonResult<GoogleProfile>> profileResponse =
            oauthRepository.getProfile(loginRequestUrl, httpEntity);

        return this.setLogin(profileResponse, sessionId);
    }

    private Optional<GoogleProfile> setLogin(final ResponseEntity<CommonResult<GoogleProfile>> profileResponse,
                                             final String sessionId) {

        HttpHeaders headers = profileResponse.getHeaders();

        if (isLoginFail(profileResponse)) {
            // 로그인 실패 시 로그인 시도한 프로필 정보를 이용하여 회원가입 진행
            GoogleProfile profile = Optional.ofNullable(profileResponse.getBody())
                                            .orElseThrow(LoginFailException::new)
                                            .getData();

            return Optional.ofNullable(profile);
        }

        String jwt = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);
        if (jwt.startsWith(JwtInfo.BEARER)) {
            jwt = jwt.substring(JwtInfo.BEARER_LENGTH);
        }

        String expireAt = Objects.requireNonNull(headers.get(JwtInfo.JWT_EXPIRE)).get(0);

        JwtInfo.saveJwt(redisTemplate, this.getMemberInfo(jwt), sessionId, jwt, expireAt);

        return Optional.empty();
    }

    private MemberInfo getMemberInfo(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        ResponseEntity<CommonResult<MemberInfo>> memberInfo = authRepository.getMemberInfo(headers);

        return Objects.requireNonNull(memberInfo.getBody()).getData();
    }

    private boolean isLoginFail(ResponseEntity<CommonResult<GoogleProfile>> profileResponse) {
        return profileResponse.getStatusCode().is4xxClientError()
            && (Objects.isNull(profileResponse.getHeaders().get(HttpHeaders.AUTHORIZATION))
            || isInvalidHeader(profileResponse.getHeaders()));
    }

    private boolean isInvalidHeader(HttpHeaders headers) {
        return Objects.isNull(headers.get(AUTHORIZATION))
            || Objects.isNull(Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0))
            || Objects.isNull(headers.get(JwtInfo.JWT_EXPIRE))
            || Objects.isNull(Objects.requireNonNull(headers.get(JwtInfo.JWT_EXPIRE)).get(0));
    }

}
