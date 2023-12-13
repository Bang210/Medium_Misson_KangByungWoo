package com.ll.medium.domain.post.entity;

import com.ll.medium.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "Text")
    private String body;

    @Column
    private String isPublished;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Member author;

    private Long hit;

    @ManyToMany
    Set<Member> recommender;


}