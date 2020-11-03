package com.dsmpear.main.exceptions;

public class UserNotMemberException extends RuntimeException {
    public UserNotMemberException(){
        super("User is Not Member!!");
    }
}
