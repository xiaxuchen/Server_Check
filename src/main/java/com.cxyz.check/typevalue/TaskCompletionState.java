package com.cxyz.check.typevalue;

/**
 * 考勤任务完成情况状态
 */
public interface TaskCompletionState {
    /**
     * 待考勤
     */
    int WAIT = 0;
    /**
     * 考勤完成
     */
    int COMPLE = 1;
    /**
     * 未考勤
     */
    int NOTCHECKED = -1;
    /**
     * 特殊情况
     */
    int OTHERSTATE = -2;
}
