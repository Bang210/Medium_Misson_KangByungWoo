package com.ll.medium.global.initData;

import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.service.PostService;
import com.ll.medium.global.system.service.SystemService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotProd {
    @Bean
    public ApplicationRunner initNotProd(

            SystemService systemService,
            MemberService memberService,
            PostService postService
    ) {
        return args -> {
            if (systemService.testDataCreated()) return;

            for (int i = 1; i <= 50; i++) {
                memberService.pay(memberService.create("paidUser%d".formatted(i), "12345678"));
                memberService.create("unpaidUser%d".formatted(i), "12345678");
                postService.create("paidPost%d".formatted(i), "content", true, memberService.getMember("paidUser%d".formatted(i)), true);
                postService.create("unpaidPost%d".formatted(i), "content", true, memberService.getMember("unpaidUser%d".formatted(i)), false);
            }
        };
    }
}
