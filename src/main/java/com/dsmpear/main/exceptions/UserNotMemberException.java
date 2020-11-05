package com.dsmpear.main.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "User is Not a Member of the Team")
public class UserNotMemberException extends RuntimeException {
}
