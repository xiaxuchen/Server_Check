package com.cxyz.check.typevalue;

public interface AuditState {

    /**
     * 待审核
     */
    Integer WAIT_AUDIT = 0;

    /**
     * 审核成功
     */
    Integer SUCCESS = 1;

    /**
     * 审核失败
     */
    Integer FAIL = -1;

    /**
     * 只是请假条不是请假(条件是没有请假审核)
     */
    Integer ONLY_VACATE = 2;
}
