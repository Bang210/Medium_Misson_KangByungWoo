package com.ll.medium.global.rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public String redirect(String path, String msg) {
        return "redirect:" + path + "?msg=" + URLEncoder.encode(msg, StandardCharsets.UTF_8) + ";ttl=" + (new Date().getTime() + 1000 * 5);
    }


    public String historyBack(String msg) {

        request.setAttribute("failMsg", msg);
        return "global/fail_msg";
    }

    public String redirectByFailure(String path, String msg) {
        return "redirect:" + path + "?failMsg=" + URLEncoder.encode(msg, StandardCharsets.UTF_8);
    }

    public User getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(it -> it instanceof User)
                .map(it -> (User) it)
                .orElse(null);
    }


    public boolean isLoggedIn() {
        return getUser() != null;
        }
}