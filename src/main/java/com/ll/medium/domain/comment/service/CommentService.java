package com.ll.medium.domain.comment.service;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.comment.repository.CommentRepository;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void create(Member member, Post post, String content) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
