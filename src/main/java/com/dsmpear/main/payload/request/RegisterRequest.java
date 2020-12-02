package com.dsmpear.main.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter @NotNull
public class RegisterRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    public Boolean isValidAddress(String validAddress) {
        String emailAddress = email.substring(email.indexOf("@") + 1);

        return validAddress.equals(emailAddress);
    }

}
