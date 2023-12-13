package com.ll.medium.domain.post.entity;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
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
    private boolean published;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Member author;

    private Long hit;

    @ManyToMany
    Set<Member> recommender;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
}