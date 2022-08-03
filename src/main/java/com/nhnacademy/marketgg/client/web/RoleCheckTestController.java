package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.annotation.RoleCheck;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.jwt.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleCheckTestController {

    @RoleCheck(accessLevel = Role.ROLE_ADMIN)
    @GetMapping("/role-test")
    public String testRoleCheck() throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        return "success";
    }

}
