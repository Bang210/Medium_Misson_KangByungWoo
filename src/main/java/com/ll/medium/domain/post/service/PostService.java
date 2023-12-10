package com.ll.medium.domain.post.service;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.repository.PostRepository;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    public void create(String title, String body, String isPublished, Member member) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setAuthor(member);
        post.setIsPublished(isPublished);
        post.setCreateDate(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<Post> findAll() {
        List<Post> postList = postRepository.findAll();
        return postList;
    }

    public Post getPostById(Long id) {
        Optional<Post> opPost = postRepository.findById(id);
        if (opPost.isPresent()) {
            return opPost.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public Page<Post> pagePublished(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByIsPublishedTrue(pageable);
    }

    public Page<Post> pageAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findAll(pageable);
    }
}
