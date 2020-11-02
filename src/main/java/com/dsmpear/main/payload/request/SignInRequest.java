package com.dsmpear.main.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @AllArgsConstructor @NoArgsConstructor
public class SignInRequest {
    @Email
    private String email;

    @NotBlank
    private String password;
}