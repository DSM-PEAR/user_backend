package com.dsmpear.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Member Already Include Exceprion")
public class MemberAlreadyIncludeException extends RuntimeException{
}
