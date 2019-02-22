package com.cxyz.check.exception.push;

public class NoPushException extends PushException{

    public NoPushException() {
    }

    public NoPushException(String message) {
        super(message);
    }

    public NoPushException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPushException(Throwable cause) {
        super(cause);
    }
}
