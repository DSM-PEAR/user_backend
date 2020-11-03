package com.dsmpear.main.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("INVALID_TOKEN");
    }
}
