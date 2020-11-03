package com.dsmpear.main.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User Not Found!!!");
    }
}
