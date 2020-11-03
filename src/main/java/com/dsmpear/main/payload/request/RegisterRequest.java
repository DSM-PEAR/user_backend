package com.dsmpear.main.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter @Setter @NotNull
public class RegisterRequest {
    private String name;
    private String id;
    private String password;

    @Email
    private String email;
}
