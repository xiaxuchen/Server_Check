package com.cxyz.check.exception;

public class InnerException extends RuntimeException{
    public InnerException() {
    }

    public InnerException(String message) {
        super(message);
    }

    public InnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InnerException(Throwable cause) {
        super(cause);
    }
}
