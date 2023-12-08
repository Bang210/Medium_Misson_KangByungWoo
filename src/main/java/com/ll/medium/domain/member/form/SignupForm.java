package com.ll.medium.domain.member.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String password_confirm;
}
