package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByPublishedTrue(Pageable pageable);
    List<Post> findByPublishedTrueOrderByCreateDateDesc();
    List<Post> findByPublishedTrueOrderByRecommenderDesc();
    Page<Post> findByAuthorId(Pageable pageable, Long UserId);
    Page<Post> findByPublishedTrueAndAuthorIdOrderByCreateDateDesc(Pageable pageable, Long authorId);
}