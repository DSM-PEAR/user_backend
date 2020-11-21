package com.dsmpear.main.exceptions;

public class UserIsAlreadyRegisteredException extends RuntimeException {
    public UserIsAlreadyRegisteredException() {
        super("User is already registered Exception");
    }
}
