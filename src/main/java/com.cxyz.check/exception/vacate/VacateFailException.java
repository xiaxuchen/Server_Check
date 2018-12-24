package com.cxyz.check.exception.vacate;

/**
 * 请假任务添加失败时抛出
 */
public class VacateFailException extends VacateException{

    public VacateFailException() {
    }

    public VacateFailException(String message) {
        super(message);
    }

    public VacateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public VacateFailException(Throwable cause) {
        super(cause);
    }
}
