package com.cxyz.check.exception;

/**
 * 用户操作时可能会抛出的异常
 */
public class UserException extends RuntimeException{
    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

}
