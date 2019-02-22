package com.cxyz.check.exception.audit;

import com.cxyz.check.exception.BaseException;

public class AuditException extends BaseException {

    public AuditException() {
    }

    public AuditException(String message) {
        super(message);
    }

    public AuditException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditException(Throwable cause) {
        super(cause);
    }
}
