package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.dto.member.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.member.EmailRequest;
import com.nhnacademy.marketgg.client.dto.member.EmailUseResponse;

public interface MemberInfoRepository {

    EmailExistResponse checkEmail(final EmailRequest emailRequest);

    EmailUseResponse useEmail(final EmailRequest emailRequest);

}
