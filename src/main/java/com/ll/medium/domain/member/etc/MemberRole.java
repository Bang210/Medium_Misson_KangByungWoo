package com.ll.medium.domain.member.etc;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),
    PAID("ROLE_PAID");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}