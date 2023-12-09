package com.ll.medium.domain.post.controller;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.form.WriteForm;
import com.ll.medium.domain.post.service.PostService;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

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
    public String showList(Model model) {
        List<Post> publishedPostList = postService.findPublished();
        model.addAttribute("postList", publishedPostList);
        return "/post/post_list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable("id") Long id) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/post/post_detail";
    }

}