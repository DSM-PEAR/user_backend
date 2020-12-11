package com.dsmpear.main.payload.request;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class EmailVerifyRequest {
    private String number;

    @Email
    private String email;
}
