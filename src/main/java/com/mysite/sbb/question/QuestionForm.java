package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목이 있어야 합니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message = "내용이 있어야 합니다.")
    private String content;
}
