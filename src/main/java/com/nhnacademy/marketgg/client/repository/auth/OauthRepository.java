package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface OauthRepository {

    <T> ResponseEntity<CommonResult<GoogleProfile>> getProfile(String loginRequestUrl, HttpEntity<T> httpEntity);

}
