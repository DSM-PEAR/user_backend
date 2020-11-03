package com.dsmpear.main.exceptions;

import com.dsmpear.main.global.error.exception.BusinessException;
import com.dsmpear.main.global.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super("User Not Found!!!", ErrorCode.BAD_REQUEST);
    }
}
