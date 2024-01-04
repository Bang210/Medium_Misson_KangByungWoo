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

    List<Post> findByPublishedTrueAndTitleContainingOrderByCreateDateDesc(String keyword);
    List<Post> findByPublishedTrueAndTitleContainingOrderByRecommenderDesc(String keyword);

    List<Post> findByPublishedTrueAndTitleContainingOrderByCreateDateAsc(String keyword);

    List<Post> findByPublishedTrueAndTitleContainingOrderByRecommenderAsc(String keyword);

    List<Post> findByPublishedTrueAndTitleContainingOrderByHitDesc(String keyword);

    List<Post> findByPublishedTrueAndTitleContainingOrderByHitAsc(String keyword);
}