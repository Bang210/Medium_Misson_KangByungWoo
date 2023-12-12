package com.ll.medium.domain.member.service;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.repository.MemberRepository;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String password) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
        return member;
    }


    public Member getMember(String username) {
        Optional<Member> opMember = memberRepository.findByUsername(username);
        if (opMember.isPresent()) {
            return opMember.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }

    public List<Member> searchByUsername(String keyword) {
        return memberRepository.findByUsernameContaining(keyword);
    }
}