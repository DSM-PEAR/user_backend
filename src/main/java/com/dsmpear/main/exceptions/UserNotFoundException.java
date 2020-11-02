package com.dsmpear.main.domain.auth.exceptions;

import com.dsmpear.main.global.error.exception.BusinessException;
import com.dsmpear.main.global.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super("User Not Found!!!", ErrorCode.BAD_REQUEST);
    }
}
