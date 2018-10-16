package com.cxyz.check.dao;

import com.cxyz.check.entity.TaskInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoDao {

    /**
     * 通过班级id获取班级的所有考勤任务,只查询tb_taskinfo
     * @param grade 班级id
     * @return
     */
    List<TaskInfo> getTaskInfos(@Param("grade")int grade);

    /**
     * 通过班级id获取班级的所有考勤任务的所有信息
     * @param grade
     * @return
     */
    List<TaskInfo> getTasks(@Param("grade") int grade);

    /**
     * 添加考勤任务
     * @param infos 考勤任务所有信息
     * TODO 需要实现xml配置
     */
    void addTasks(@Param("infos") List<TaskInfo> infos);
}
