package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;

public interface MemberInfoRepository {

    EmailExistResponse checkEmail(final EmailRequest emailRequest);

    EmailUseResponse useEmail(final EmailRequest emailRequest);

}
