package com.cxyz.check.dao;

import com.cxyz.check.entity.TaskCompletion;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskCompletionDao {

    /**
     * 通过考勤基本信息的id获取所有的完成情况
     * @param tid 考勤基本信息的id
     * @return
     */
    List<TaskCompletion> getCompByTID(@Param("tid") String tid);

    /**
     * 通过考勤完成情况的id获取完整的考勤情况信息
     * @param id 考勤完成情况id
     * @return
     */
    TaskCompletion getTaskCompById(@Param("id")int id);

    /**
     * 添加考勤任务的考勤完成情况
     * @param taskCompletions
     * @return
     */
    void addTasksOfComp(@Param("comp") List<TaskCompletion> comp);
}
