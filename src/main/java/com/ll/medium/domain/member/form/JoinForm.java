package com.ll.medium.domain.member.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinForm {

    @Size(min = 5, max = 25)
    @NotBlank
    private String username;

    @Size(min = 8, max = 25)
    @NotBlank
    private String password;

    @NotBlank
    private String password_confirm;
}
