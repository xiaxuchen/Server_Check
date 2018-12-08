package com.cxyz.check.exception.record;

import com.cxyz.check.dao.RecordDao;

/**
 * 当前暂无历史记录
 */
public class NOHistoryException extends RecordException {

    public NOHistoryException() {
    }

    public NOHistoryException(String message) {
        super(message);
    }

    public NOHistoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
