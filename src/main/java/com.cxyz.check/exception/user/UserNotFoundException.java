package com.cxyz.check.exception.user;

/**
 * 找不到用户抛出的异常
 */
public class UserNotFoundException extends UserException{

    public UserNotFoundException() {

    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
