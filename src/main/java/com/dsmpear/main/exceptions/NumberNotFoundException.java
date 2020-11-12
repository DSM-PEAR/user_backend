package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Failed to find number by email")
public class NumberNotFoundException extends RuntimeException {
    public NumberNotFoundException() {
        super("Failed to find number by email!!");
    }
}
