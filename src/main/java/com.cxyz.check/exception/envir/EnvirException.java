package com.cxyz.check.exception.envir;

public class EnvirException extends RuntimeException {
    public EnvirException() {
    }

    public EnvirException(String message) {
        super(message);
    }

    public EnvirException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvirException(Throwable cause) {
        super(cause);
    }
}
