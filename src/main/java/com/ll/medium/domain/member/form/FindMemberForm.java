package com.ll.medium.domain.member.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindMemberForm {

    @NotBlank
    private String keyword;
}
