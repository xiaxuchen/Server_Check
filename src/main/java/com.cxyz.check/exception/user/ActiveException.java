package com.cxyz.check.exception.user;

/**
 * 账号激活异常
 */
public class ActiveException extends UserException {

    public ActiveException() {
    }

    public ActiveException(String message) {
        super(message);
    }

    public ActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActiveException(Throwable cause) {
        super(cause);
    }
}
