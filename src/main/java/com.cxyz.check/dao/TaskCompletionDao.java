package com.cxyz.check.dao;

import com.cxyz.check.entity.TaskCompletion;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskCompletionDao {


    /**
     * 通过班级id和当前时间获取考勤情况
     * @param grade 班级id
     * @param time 当前时间
     * @param date 当前日期
     * @return
     */
    List<TaskCompletion> selectOne(@Param("grade") int grade, @Param("time") String time, @Param("date")String date);


    /**
     * 更新考勤情况的状态
     * @param state 状态
     * @param id 考勤完成情况id
     * @return
     */
    int updateCompState(@Param("state") int state,@Param("id") int id);
}
