package com.cxyz.check.entity.typevalue;

/**
 * 考勤任务完成情况状态
 */
public interface TaskState {
    /**
     * 待考勤
     */
    public static final int WAIT = 0;
    /**
     * 考勤完成
     */
    public static final int COMPLE = 1;
    /**
     * 未考勤
     */
    public static final int NOTCHECKED = -2;
    /**
     * 特殊情况
     */
    public static final int OTHERSTATE = -1;
}
