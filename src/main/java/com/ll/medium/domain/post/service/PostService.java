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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    @Transactional
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

    public List<Post> findRecent(int num) {
        List<Post> recentList = postRepository.findByIsPublishedTrueOrderByCreateDateDesc();
        num = Math.min(num, recentList.size());
        return recentList.stream().limit(num).toList();
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

    @Transactional
    public void modify(Post post, String title, String body, String isPublished) {
        post.setTitle(title);
        post.setBody(body);
        post.setIsPublished(isPublished);
        post.setModifyDate(LocalDateTime.now());
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Page<Post> getPageMyPost(int page, Long id) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByAuthorId(pageable, id);
    }

    public Page<Post> getPageMemberPost(int page, Long id) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByIsPublishedTrueAndAuthorIdOrderByCreateDateDesc(pageable, id);
    }
}