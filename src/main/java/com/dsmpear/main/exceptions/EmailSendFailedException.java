package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Email Send Failed!!")
public class EmailSendFailedException extends RuntimeException {
    public EmailSendFailedException() {
        super("Email send failed.");
    }
}
