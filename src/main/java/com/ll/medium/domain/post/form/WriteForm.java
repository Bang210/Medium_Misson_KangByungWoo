package com.ll.medium.domain.post.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteForm {
    @NotBlank
    @Size(max=100)
    private String title;

    @NotBlank
    private String body;
}
