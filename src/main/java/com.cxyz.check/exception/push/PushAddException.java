package com.cxyz.check.exception.push;

public class PushAddException extends PushException{

    public PushAddException() {
    }

    public PushAddException(String message) {
        super(message);
    }

    public PushAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushAddException(Throwable cause) {
        super(cause);
    }
}
