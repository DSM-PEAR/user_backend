package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User Not Accessible")
public class UserNotAccessibleException extends RuntimeException{
}
