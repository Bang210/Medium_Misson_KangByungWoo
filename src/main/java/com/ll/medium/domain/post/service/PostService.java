package com.ll.medium.domain.post.service;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.repository.PostRepository;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<Post> findPublished() {
        List<Post> postList = postRepository.findAll();
        List<Post> publishedPostList = postList.stream()
                .filter(post -> post.getIsPublished().equals("true"))
                .toList();
        return publishedPostList;
    }
}
