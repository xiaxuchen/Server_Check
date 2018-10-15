package com.cxyz.check.dao;

import com.cxyz.check.entity.TaskInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoDao {

    /**
     * 通过班级id获取班级的所有考勤任务
     * @param grade 班级id
     * @return
     */
    TaskInfo getTaskInfos(int grade);

    /**
     * 添加考勤任务的考勤基本信息
     * @param infos 考勤基本信息
     * TODO 需要实现xml配置
     */
    void addTasks(@Param("infos") List<TaskInfo> infos);
}
