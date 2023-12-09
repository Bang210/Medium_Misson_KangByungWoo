package com.ll.medium.domain.post.service;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    public void create(String title, String body, Member member) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setAuthor(member);
        post.setCreateDate(LocalDateTime.now());
        postRepository.save(post);
    }
}
