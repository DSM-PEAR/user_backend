package com.dsmpear.main.exceptions;

import com.dsmpear.main.global.error.exception.BusinessException;
import com.dsmpear.main.global.error.exception.ErrorCode;

public class UserNotMemberException extends BusinessException {
    public UserNotMemberException(){
        super(ErrorCode.USER_NOT_MEMBER);
    }
}
