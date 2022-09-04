package com.nhnacademy.marketgg.client.service.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.member.LoginRequest;
import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.client.util.LoginStatus;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * 로그인, 로그아웃 로직을 실행하는 구현체입니다.
 * {@link AuthService}
 *
 * @author 윤동열
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String WITHDRAW = "WITHDRAW";

    private final AuthRepository authRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginStatus doLogin(final LoginRequest loginRequest, final String sessionId) {

        ResponseEntity<Void> response;

        try {
            response = authRepository.doLogin(loginRequest, sessionId);
            if (!response.getStatusCode().is4xxClientError() && isWithdraw(response)) {
                return LoginStatus.WITHDRAW;
            }
        } catch (HttpClientErrorException e) {
            log.error(e.toString());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return LoginStatus.UNAUTHORIZED;
            }
            throw new UnAuthenticException();
        }

        HttpHeaders headers = Objects.requireNonNull(response).getHeaders();

        String jwt = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);
        if (jwt.startsWith(JwtInfo.BEARER)) {
            jwt = jwt.substring(7);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);

        MemberInfo memberInfo = Objects.requireNonNull(authRepository.getMemberInfo(httpHeaders)
                                                                     .getBody())
                                       .getData();
        String expireAt = Objects.requireNonNull(headers.get(JwtInfo.JWT_EXPIRE)).get(0);

        JwtInfo.saveJwt(redisTemplate, memberInfo, sessionId, jwt, expireAt);

        return LoginStatus.LOGIN;
    }

    private boolean isWithdraw(ResponseEntity<Void> response) {
        return Objects.nonNull(response.getHeaders().get(WWW_AUTHENTICATE))
            && Objects.requireNonNull(response.getHeaders().get(WWW_AUTHENTICATE)).contains(WITHDRAW);
    }

    @Override
    public void logout() {
        String sessionId = SessionContext.getSessionId();
        if (Objects.isNull(redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY))) {
            log.info("already logout");
            return;
        }

        try {
            authRepository.logout();
        } catch (HttpServerErrorException e) {
            log.error(e.toString());
        } finally {
            redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
        }

    }

}
