package com.ll.medium.domain.comment.entity;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Member member;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
