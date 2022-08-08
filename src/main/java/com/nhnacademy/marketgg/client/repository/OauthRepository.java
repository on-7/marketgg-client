package com.nhnacademy.marketgg.client.repository;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface OauthRepository {

    <T> ResponseEntity<String> getProfile(String loginRequestUrl, HttpEntity<T> httpEntity);

}
