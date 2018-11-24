package com.cxyz.check.exception.util;

public class GsonException extends Exception{
    public GsonException() {
    }

    public GsonException(String message) {
        super(message);
    }

    public GsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public GsonException(Throwable cause) {
        super(cause);
    }
}
