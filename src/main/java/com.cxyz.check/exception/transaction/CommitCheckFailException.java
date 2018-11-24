package com.cxyz.check.exception.transaction;

public class CommitCheckFailException extends TransactionalException{

    public CommitCheckFailException() {
    }

    public CommitCheckFailException(String message) {
        super(message);
    }

    public CommitCheckFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommitCheckFailException(Throwable cause) {
        super(cause);
    }
}
