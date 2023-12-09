package com.ll.medium.global.rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public String redirect(String path, String msg) {
        return "redirect:" + path + "?msg=" + URLEncoder.encode(msg, StandardCharsets.UTF_8);
    }


    public String historyBack(String msg) {

        request.setAttribute("failMsg", msg);
        return "global/fail_msg";
    }

    public String redirectByFailure(String path, String msg) {
        return "redirect:" + path + "?failMsg=" + URLEncoder.encode(msg, StandardCharsets.UTF_8);
    }
}