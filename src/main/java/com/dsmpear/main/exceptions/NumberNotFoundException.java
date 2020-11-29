package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Number Not Found!!")
public class NumberNotFoundException extends RuntimeException {
    public NumberNotFoundException() {
        super("Number not found!!");
    }
}
