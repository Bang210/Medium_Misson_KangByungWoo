package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}