package com.cxyz.check.exception.record;

/**
 * 没有更多考勤任务
 */

public class NoMoreHistoryException extends NOHistoryException{

    public NoMoreHistoryException() {
    }

    public NoMoreHistoryException(String message) {
        super(message);
    }

    public NoMoreHistoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
