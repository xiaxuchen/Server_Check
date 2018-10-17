package com.cxyz.check.entity.typevalue;

/**
 * 考勤记录结果
 */
public interface ResultType {
    /**
     * 请假
     */
    public static final int VACATE = 1;
    /**
     * 早退
     */
    public static final int EARLYLEAVE = 4;
    /**
     * 迟到
     */
    public static final int LATE = 0;
    /**
     * 缺勤
     */
    public static final int ABSENTEEISM = 3;
}
