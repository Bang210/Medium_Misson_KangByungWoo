package com.ll.medium.domain.member.controller;


import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.SignupForm;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/signup")
    public String showSignup() {
        return "member/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm) {

        Member member = memberService.create(signupForm.getUsername(), signupForm.getPassword());

        return rq.redirect("/", "회원가입 성공");
    }
}