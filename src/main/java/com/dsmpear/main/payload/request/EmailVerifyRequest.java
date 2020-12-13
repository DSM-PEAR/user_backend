package com.dsmpear.main.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter @AllArgsConstructor
public class EmailVerifyRequest {
    private String number;

    @Email
    private String email;
}
