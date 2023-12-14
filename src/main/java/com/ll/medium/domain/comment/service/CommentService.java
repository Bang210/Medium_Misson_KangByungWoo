package com.ll.medium.domain.comment.service;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.comment.repository.CommentRepository;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Comment findById(Long id) {

        Optional<Comment> opComment = commentRepository.findById(id);
        if (opComment.isPresent()) {
            return opComment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Transactional
    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
