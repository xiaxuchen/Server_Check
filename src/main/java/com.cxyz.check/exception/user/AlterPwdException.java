package com.cxyz.check.exception.user;

public class AlterPwdException extends UserException{

    public AlterPwdException() {
    }

    public AlterPwdException(String message) {
        super(message);
    }

    public AlterPwdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlterPwdException(Throwable cause) {
        super(cause);
    }
}
