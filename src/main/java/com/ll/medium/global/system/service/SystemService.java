package com.ll.medium.global.system.service;

import com.ll.medium.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final MemberRepository memberRepository;

    public boolean testDataCreated() {
        return memberRepository.count() > 0;
    }
}
