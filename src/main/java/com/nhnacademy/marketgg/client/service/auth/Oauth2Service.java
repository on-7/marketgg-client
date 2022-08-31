package com.nhnacademy.marketgg.client.service.auth;

import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import java.util.Optional;

/**
 * 소셜 로그인 로직을 처리합니다.
 *
 * @author 윤동열
 */
public interface Oauth2Service {

    String getRedirectUrl();

    Optional<GoogleProfile> attemptLogin(final String code, final String sessionId);

}
