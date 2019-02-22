package com.cxyz.check.exception.push;

public class PushException extends RuntimeException{

    public PushException() {
    }

    public PushException(String message) {
        super(message);
    }

    public PushException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushException(Throwable cause) {
        super(cause);
    }
}
