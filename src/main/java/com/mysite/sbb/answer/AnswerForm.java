package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AnswerForm {
    @NotEmpty(message = "내용이 있어야 합니다.")
    private String content;
}
