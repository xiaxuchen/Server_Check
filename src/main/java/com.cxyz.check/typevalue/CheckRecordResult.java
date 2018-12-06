package com.cxyz.check.typevalue;

/**
 * 考勤记录结果
 */
public interface CheckRecordResult {
    /**
     * 请假
     */
    int VACATE = 0;
    /**
     * 早退
     */
    int EARLYLEAVE = -3;
    /**
     * 迟到
     */
    int LATE = -2;
    /**
     * 缺勤
     */
    int ABSENTEEISM = -1;
}
