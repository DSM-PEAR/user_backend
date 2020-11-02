package com.dsmpear.main.domain.questions.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @Email
    private String email;

    @NotBlank
    private String description;
}
