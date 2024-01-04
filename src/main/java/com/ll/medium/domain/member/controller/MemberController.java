package com.ll.medium.domain.member.controller;


import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.FindMemberForm;
import com.ll.medium.domain.member.form.JoinForm;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.service.PostService;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;
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
        return rq.redirect("/member/login", "회원가입 성공, 로그인 후 이용해주세요.");
    }

    @GetMapping("/login")
    public String showLogin() {
        if (rq.isLoggedIn()) {
            return rq.redirectByFailure("/post/main","이미 로그인되어 있습니다.");
        }
        return "member/login_form";
    }

    @PostMapping("/find")
    public String showFindMember(

            @Valid FindMemberForm findMemberForm,
            Model model
    ) {
        String keyword = findMemberForm.getKeyword();
        List<Member> memberList = memberService.searchByUsername(keyword);
        List<Post> searchedPostList = postService.search(findMemberForm.getKey(), findMemberForm.getCriteria(), keyword);
        model.addAttribute("memberList", memberList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchedPostList", searchedPostList);
        return "member/find_form";
    }

    @PostMapping("/pay/{id}")
    public String memberPay(
        @PathVariable("id") Long id
    ) {
        Member member = memberService.getMemberById(id);
        if (member.isPaid()) {
            return rq.redirectByFailure("/post/mypost","이미 멤버쉽 적용중인 회원입니다.");
        }
        memberService.pay(member);
        return rq.redirect("/post/mypost", "유료 멤버쉽이 적용되었습니다.");
    }

    @PostMapping("/unpay/{id}")
    public String memberUnpay(
            @PathVariable("id") Long id
    ) {
        Member member = memberService.getMemberById(id);
        if (!member.isPaid()) {
            return rq.redirectByFailure("/post/mypost","적용중인 멤버쉽이 없습니다.");
        }
        memberService.unpay(member);
        return rq.redirect("/post/mypost", "유료 멤버쉽이 해지되었습니다.");
    }
}