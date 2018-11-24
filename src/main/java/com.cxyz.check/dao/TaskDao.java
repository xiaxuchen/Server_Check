package com.cxyz.check.dao;

import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.SubjectsDto;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.exception.task.NoTaskException;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 管理考勤数据
 */
public interface TaskDao {
    /**
     * 通过以下参数获取当前是否有考勤任务
     * @param checker_id 考勤人id
     * @param checker_type 考勤人类型
     * @param date 考勤日期
     * @param time 当前时间
     * @param type 考勤的类型
     * @return 考勤任务Dto
     */
    TaskCompletion checkTask(@Param("checker_id")String checker_id,
                             @Param("checker_type")int checker_type,
                             @Param("date")String date,
                             @Param("type") int type,
                             @Param("time") String time);

    /**
     * 更新考勤完成情况
     * @param id
     * @param state
     * @return 影响记录数
     */
    int updateComp(@Param("id") int id,@Param("state") int state);

    /**
     * 获取考勤日期
     * @param id 考勤完成情况id
     * @return
     */
    Date getCompDate(@Param("id") int id);

    /**
     * 获取班级总考勤数量
     * @param grade 班级id
     * @return
     */
    int getGradeCheck(@Param("grade") int grade);


    int addComp(@Param("date") String date,@Param("id") int id);

    /**
     * 获取所有的任务信息
     * @param grade 班级id
     * @return
     */
    SubjectsDto getLessons(int grade);

}
