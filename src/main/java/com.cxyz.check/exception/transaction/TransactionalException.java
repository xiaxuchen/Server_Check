package com.cxyz.check.exception.transaction;

public class TransactionalException extends RuntimeException {
    public TransactionalException() {
    }

    public TransactionalException(String message) {
        super(message);
    }

    public TransactionalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalException(Throwable cause) {
        super(cause);
    }
}
