package com.cxyz.check.exception;

/**
 * 事务提交失败抛出的异常
 */
public class CommitFailException extends BaseException{
    public CommitFailException() {
    }

    public CommitFailException(String message) {
        super(message);
    }

    public CommitFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommitFailException(Throwable cause) {
        super(cause);
    }
}
