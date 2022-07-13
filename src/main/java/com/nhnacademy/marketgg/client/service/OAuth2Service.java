package com.nhnacademy.marketgg.client.service;

public interface OAuth2Service {

    String getRedirectUrl();

    String getToken(String code);
}
