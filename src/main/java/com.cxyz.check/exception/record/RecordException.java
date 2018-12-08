package com.cxyz.check.exception.record;

public class RecordException extends RuntimeException{

    public RecordException() {
    }

    public RecordException(String message) {
        super(message);
    }

    public RecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
