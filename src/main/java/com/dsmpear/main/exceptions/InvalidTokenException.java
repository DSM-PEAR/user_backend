package com.dsmpear.main.exceptions;

import com.dsmpear.main.global.error.exception.BusinessException;
import com.dsmpear.main.global.error.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
