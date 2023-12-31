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

    //글쓰기 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite(
            Model model,
            Principal principal
    ) {
        Member member = memberService.getMember(principal.getName());
        model.addAttribute("member", member);
        return "post/write_form";
    }

    //글쓰기 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(

            @Valid WriteForm writeForm,
            Principal principal
    ) {


        Member member = memberService.getMember(principal.getName());
        if (writeForm.isPaid() && !member.isPaid()) {
            rq.historyBack("유료 글은 유료 회원만 등록 가능합니다.");
        }
        postService.create(writeForm.getTitle(), writeForm.getBody(), writeForm.isPublished(), member, writeForm.isPaid());
        return rq.redirect(

                "/post/main",
                "%s 글이 %s로 등록되었습니다.".formatted(writeForm.isPaid()? "유료" : "무료", writeForm.isPublished()? "공개" : "비공개")
        );
    }

    //글 목록
    @GetMapping("/list")
    public String showList(

            Model model,
            @RequestParam(value="page", defaultValue = "0") int page
    ) {
        Page<Post> paging = postService.pagePublished(page);
        model.addAttribute("page", page);
        model.addAttribute("paging", paging);
        return "post/post_list";
    }

    //글 상세 페이지
    @GetMapping("/detail/{id}")
    public String showDetail(

            Model model,
            @PathVariable("id") Long id

    ) {
        Post post = postService.getPostById(id);
        //유료 글 처리
        if (post.isPaid()) {
            if (!rq.isLoggedIn()) {
                return rq.historyBack("로그인이 필요한 서비스입니다.");
            } else if (!memberService.getMember(rq.getUser().getUsername()).isPaid() && memberService.getMember(rq.getUser().getUsername()) != post.getAuthor()) {
                return rq.historyBack("유료회원만 조회 가능한 글입니다.");
            }
        }

        List<String> recommenderNames = postService.getRecommenderNames(post);
        String recommendButton = rq.isLoggedIn() && recommenderNames.contains(rq.getUser().getUsername())? "pressed" : "notPressed";
        model.addAttribute("post", post);
        model.addAttribute("recommendButton", recommendButton);
        return "post/post_detail";
    }

    //조회수 증가
    @PostMapping("/detail/{id}/increaseHit")
    public String increaseHit(

            @PathVariable("id") Long id
    ) {
        Post post = postService.getPostById(id);
        if (post.isPaid()) {
            if (!rq.isLoggedIn()) {
                return rq.historyBack("로그인이 필요한 서비스입니다.");
            } else if (!memberService.getMember(rq.getUser().getUsername()).isPaid() && memberService.getMember(rq.getUser().getUsername()) != post.getAuthor()) {
                return rq.historyBack("유료회원만 조회 가능한 글입니다.");
            }
        }
        postService.increaseHit(post);
        return "redirect:/post/detail/{id}";
    }


    //메인 페이지, 글 일부 표시
    @GetMapping("/main")
    public String home(Model model) {
        List<Post> recentList = postService.findRecent(5);
        List<Post> recommendedList = postService.findRecommended(5);
        model.addAttribute("recentList", recentList);
        model.addAttribute("recommendedList", recommendedList);
        return "home/main";
    }

    //글 수정 페이지
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

    //글 수정 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(
            @PathVariable("id") Long id,
            @Valid ModifyForm modifyForm
    ) {
        Post post = postService.getPostById(id);
        postService.modify(post, modifyForm.getTitle(), modifyForm.getBody(), modifyForm.isPublished());
        return rq.redirect(

                modifyForm.isPublished()?
                        "/post/detail/{id}" : "/post/list",
                "글이 수정되었습니다(%s).".formatted(modifyForm.isPublished()? "공개" : "비공개")
        );
    }

    //글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(

            Principal principal,
            @PathVariable("id") Long id
    ) {
        Post post = postService.getPostById(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            return rq.redirectByFailure("/post/detail/{id}", "삭제 권한이 없습니다.");
        }
        postService.delete(post);
        return rq.redirect("/post/list", "글이 삭제되었습니다.");
    }

    //글 삭제 확인
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/confirmDelete/{id}")
    public String confirmDelete(

            Principal principal,
            @PathVariable("id") Long id
    ) {
        Post post = postService.getPostById(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            return rq.redirectByFailure("/post/detail/{id}", "삭제 권한이 없습니다.");
        }
        return "post/deleteConfirm_form";
    }

    //내 글 보기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypost")
    public String myPost(

            Principal principal,
            Model model,
            @RequestParam(value="page", defaultValue = "0") int page
    ) {
        Member member = memberService.getMember(principal.getName());
        Page<Post> paging = postService.getPageMyPost(page, member.getId());
        model.addAttribute("page", page);
        model.addAttribute("paging", paging);
        model.addAttribute("member", member);
        return "post/myPost_form";
    }

    //사용자별 글 보기
    @GetMapping("/{username}/list")
    public String memberPostList(

            @PathVariable("username") String username,
            Model model,
            @RequestParam(value="page", defaultValue = "0") int page
    ) {
        Member member = memberService.getMember(username);
        Page<Post> pageMemberPost = postService.getPageMemberPost(page, member.getId());
        model.addAttribute("member", member);
        model.addAttribute("page", page);
        model.addAttribute("pageMemberPost", pageMemberPost);
        return "post/memberpost_list";
    }

    //추천
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/recommend/{id}")
    public String recommendPost(

            Principal principal,
            @PathVariable("id") Long id
    ) {
        Post post = postService.getPostById(id);
        Member member = memberService.getMember(principal.getName());
        postService.recommend(post, member);
        return rq.redirect("/post/detail/{id}","추천되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unrecommend/{id}")
    public String unrecommendPost(

            Principal principal,
            @PathVariable("id") Long id
    ) {
        Post post = postService.getPostById(id);
        Member member = memberService.getMember(principal.getName());
        postService.unrecommend(post, member);
        return rq.redirect("/post/detail/{id}","추천이 취소되었습니다.");
    }

}