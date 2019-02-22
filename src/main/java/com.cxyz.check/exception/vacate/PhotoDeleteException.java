package com.cxyz.check.exception.vacate;

public class PhotoDeleteException extends VacateException {

    public PhotoDeleteException() {
    }

    public PhotoDeleteException(String message) {
        super(message);
    }

    public PhotoDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoDeleteException(Throwable cause) {
        super(cause);
    }
}
