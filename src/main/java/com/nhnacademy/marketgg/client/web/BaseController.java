package com.nhnacademy.marketgg.client.web;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class BaseController implements Controller {

    @ModelAttribute(name = "isAdmin")
    public String isAdmin() {
        List<GrantedAuthority> list =
                new ArrayList<>(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        if(list.get(0).getAuthority().compareTo("ROLE_ADMIN") == 0) {
            return "yes";
        }

        return "no";
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

}
