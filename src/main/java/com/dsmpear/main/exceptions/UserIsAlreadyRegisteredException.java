package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User is Already Registered!!!")
public class UserIsAlreadyRegisteredException extends RuntimeException {
    public UserIsAlreadyRegisteredException() {
        super("User is already registered Exception");
    }
}
