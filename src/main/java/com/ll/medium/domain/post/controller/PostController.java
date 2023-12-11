package com.ll.medium.domain.post.controller;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.form.ModifyForm;
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
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final Rq rq;

    //글쓰기 화면
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "/post/write_form";
    }

    //글쓰기 처리
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

    //글 목록
    @GetMapping("/list")
    public String showList(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<Post> paging = postService.pagePublished(page);
        model.addAttribute("page", page);
        model.addAttribute("paging", paging);
        return "/post/post_list";
    }

    //글 상세 페이지
    @GetMapping("/detail/{id}")
    public String showDetail(

            Model model,
            @PathVariable("id") Long id

    ) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "/post/post_detail";
    }

    //테스트데이타 생성
    @GetMapping("/createTestData")
    public String createTestData() {
        for (int i = 1; i < 101; i++) {
            String title = "test[%d]".formatted(i);
            String body = "body";
            Member member = memberService.getMember("user1");
            postService.create(title, body, "true", member);
        }
        return rq.redirect("/post/main", "테스트데이터 생성");
    }

    //메인 페이지에 글 일부 표시
    @GetMapping("/main")
    public String home(Model model) {
        List<Post> recentList = postService.findRecent(5);
        model.addAttribute("recentList", recentList);
        return "home/main";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String showModify(
            Model model,
            Principal principal,
            @PathVariable("id") Long id
    ) {
        Post post = postService.getPostById(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            return rq.redirectByFailure("/post/detail/{id}", "수정 권한이 없습니다.");
        }
        model.addAttribute("post", post);
        return "post/modify_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(
            @PathVariable("id") Long id,
            @Valid ModifyForm modifyForm
    ) {
        if (modifyForm.getIsPublished() == null) {
            modifyForm.setIsPublished("false");
        }
        Post post = postService.getPostById(id);
        postService.modify(post, modifyForm.getTitle(), modifyForm.getBody(), modifyForm.getIsPublished());
        return rq.redirect(modifyForm.getIsPublished().equals("true")? "/post/detail/{id}" : "/post/list", "글이 수정되었습니다(%s).".formatted(modifyForm.getIsPublished().equals("true")? "공개" : "비공개"));
    }


}