package com.dsmpear.main.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter @AllArgsConstructor @NoArgsConstructor
public class EmailVerifyRequest {
    private String number;

    @Email
    private String email;
}
