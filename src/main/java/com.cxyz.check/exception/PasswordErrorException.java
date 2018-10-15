package com.cxyz.check.exception;

/**
 * 密码错误时抛出的异常
 */
public class PasswordErrorException extends UserException{
    public PasswordErrorException() {
    }

    public PasswordErrorException(String message) {
        super(message);
    }

    public PasswordErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordErrorException(Throwable cause) {
        super(cause);
    }
}
