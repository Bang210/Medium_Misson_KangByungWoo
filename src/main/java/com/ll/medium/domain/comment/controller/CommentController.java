package com.ll.medium.domain.comment.controller;

import com.ll.medium.domain.comment.entity.Comment;
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
    public String write(

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

    @PostMapping("{postId}/delete/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public String delete(

            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            Principal principal
    ) {
        Member member = memberService.getMember(principal.getName());
        Comment comment = commentService.findById(commentId);

        if (member.getId() != comment.getMember().getId()){
            rq.redirectByFailure("/post/detail/{postId}", "삭제 권한이 없습니다.");
        }

        commentService.delete(comment);
        return rq.redirect("/post/detail/{postId}", "댓글이 삭제되었습니다.");
    }
}
