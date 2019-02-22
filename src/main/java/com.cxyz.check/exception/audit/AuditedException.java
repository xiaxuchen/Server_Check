package com.cxyz.check.exception.audit;

/**
 * 已审核异常
 */
public class AuditedException extends AuditException{

    public AuditedException() {
    }

    public AuditedException(String message) {
        super(message);
    }

    public AuditedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditedException(Throwable cause) {
        super(cause);
    }
}
