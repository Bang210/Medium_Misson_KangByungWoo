package com.ll.medium.domain.member.controller;


import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.FindMemberForm;
import com.ll.medium.domain.member.form.JoinForm;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/join")
    public String showSignup() {
        if (rq.isLoggedIn()) {
            return rq.redirectByFailure("/post/main","이미 로그인되어 있습니다.");
        }
        return "member/join_form";
    }

    @PostMapping("/join")
    public String signup(@Valid JoinForm joinForm) {

        try {
            memberService.create(joinForm.getUsername(), joinForm.getPassword());
        } catch (DataIntegrityViolationException e) {
            return rq.historyBack("이미 존재하는 회원 아이디입니다.");
        }
        return rq.redirect("/post/main", "회원가입 성공");
    }

    @GetMapping("/login")
    public String showLogin() {
        if (rq.isLoggedIn()) {
            return rq.redirectByFailure("/post/main","이미 로그인되어 있습니다.");
        }
        return "member/login_form";
    }

    @GetMapping("/find")
    public String showFindMember(

            @Valid FindMemberForm findMemberForm,
            Model model
    ) {
        String keyword = findMemberForm.getKeyword();
        List<Member> memberList = memberService.searchByUsername(keyword);
        model.addAttribute(memberList);
        model.addAttribute(keyword);
        return "/member/find_form";
    }

}