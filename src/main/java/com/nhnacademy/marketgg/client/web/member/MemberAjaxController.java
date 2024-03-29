package com.nhnacademy.marketgg.client.web.member;

import static org.springframework.http.HttpStatus.OK;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.member.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.member.EmailRequest;
import com.nhnacademy.marketgg.client.dto.member.EmailUseResponse;
import com.nhnacademy.marketgg.client.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/marketgg/members")
public class MemberAjaxController {

    private final MemberService memberService;

    /**
     * Rest 로 통신을 요청함. 요청한 이메일이 중복되는지 확인합니다.
     *
     * @param emailRequest - 회원가입에 필요한 요청 정보 객체
     * @return 해당 이메일이 중복되는지 중복되지 않는지의 정보를 담고있는 응답 정보 객체
     */
    @PostMapping("/email")
    public ResponseEntity<CommonResult<EmailExistResponse>> checkEmail(@RequestBody final EmailRequest emailRequest) {
        log.info(emailRequest.getEmail());
        return ResponseEntity.status(OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResult.success(memberService.checkEmail(emailRequest)));
    }

    /**
     * Rest 로 통신을 요청함. 요청한 이메일이 사용가능한지 확인합니다.
     *
     * @return 해당 이메일이 사용가능한지를 확인하는 정보를 담고있는 응답 정보 객체
     */
    @PostMapping("/use/email")
    public ResponseEntity<CommonResult<EmailUseResponse>> useEmail(@RequestBody final EmailRequest emailRequest) {
        return ResponseEntity.status(OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(CommonResult.success(memberService.useEmail(emailRequest)));
    }

}
