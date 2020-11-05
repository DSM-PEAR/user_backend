package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid Email Address")
public class InvalidEmailAddressException extends RuntimeException {
    public InvalidEmailAddressException() {
        super("INVALID_EMAIL_ADDRESS");
    }
}
