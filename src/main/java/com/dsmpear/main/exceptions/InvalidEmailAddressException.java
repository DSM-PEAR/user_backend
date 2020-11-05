package com.dsmpear.main.exceptions;

public class InvalidEmailAddressException extends RuntimeException {
    public InvalidEmailAddressException() {
        super("INVALID_EMAIL_ADDRESS");
    }
}
