package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Secret Key is Not Matched!!")
public class SecretKeyNotMatchedException extends RuntimeException {
    public SecretKeyNotMatchedException() {
        super("Secret Key is Not Matched!!");
    }
}
