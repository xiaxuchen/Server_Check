package com.cxyz.check.typevalue;

/**
 * 考勤记录结果
 */
public interface CheckRecordResult {
    /**
     * 请假
     */
    public static final int VACATE = 0;
    /**
     * 早退
     */
    public static final int EARLYLEAVE = -3;
    /**
     * 迟到
     */
    public static final int LATE = -2;
    /**
     * 缺勤
     */
    public static final int ABSENTEEISM = -1;
}
