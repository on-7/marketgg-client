package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import java.util.Optional;

public interface Oauth2Service {

    String getRedirectUrl();

    Optional<GoogleProfile> getToken(final String code, final String sessionId) throws JsonProcessingException;

}
