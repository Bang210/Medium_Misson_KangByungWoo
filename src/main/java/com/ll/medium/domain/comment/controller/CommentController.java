package com.ll.medium.domain.comment.controller;

import com.ll.medium.domain.comment.form.CommentForm;
import com.ll.medium.domain.comment.service.CommentService;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.service.PostService;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {

    private final MemberService memberService;
    private final CommentService commentService;
    private final PostService postService;
    private final Rq rq;

    @PostMapping("/write/{id}")
    @PreAuthorize("isAuthenticated()")
    public String writeComment(

            @PathVariable("id") Long id,
            Principal principal,
            @Valid CommentForm commentForm
    ) {
        Member member = memberService.getMember(principal.getName());
        Post post = postService.getPostById(id);
        String content = commentForm.getContent();
        commentService.create(member, post, content);

        return rq.redirect("/post/detail/{id}", "댓글이 등록되었습니다.");
    }
}
