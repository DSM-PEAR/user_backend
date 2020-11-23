package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid Number Exception")
public class InvalidVerifyNumberException extends RuntimeException {
     public InvalidVerifyNumberException() {
         super("Invalid number exception!!");
     }
}
