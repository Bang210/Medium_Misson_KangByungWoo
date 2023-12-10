package com.ll.medium.domain.post.controller;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.form.WriteForm;
import com.ll.medium.domain.post.service.PostService;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final Rq rq;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "/post/write_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@Valid WriteForm writeForm, Principal principal) {
        if (writeForm.getIsPublished() == null) {
            writeForm.setIsPublished("false");
        }

        Member member = memberService.getMember(principal.getName());
        postService.create(writeForm.getTitle(), writeForm.getBody(), writeForm.getIsPublished(), member);
        return rq.redirect("/", "글이 %s로 등록되었습니다.".formatted(writeForm.getIsPublished().equals("true")? "공개" : "비공개"));
    }

    @GetMapping("/list")
    public String showList(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<Post> paging = postService.pagePublished(page);
        model.addAttribute("page", page);
        model.addAttribute("paging", paging);
        return "/post/post_list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Long id) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/post/post_detail";
    }

    @GetMapping("/createTestData")
    public String createTestData() {
        for (int i = 0; i < 100; i++) {
            String title = "test[%d]".formatted(i);
            String body = "body";
            Member member = memberService.getMember("user1");
            postService.create(title, body, "true", member);
        }
        return rq.redirect("/", "테스트데이터 생성");
    }

}