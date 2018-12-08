package com.cxyz.check.dao;

import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.SubjectsDto;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.exception.task.NoTaskException;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

import javafx.concurrent.Task;

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
    TaskCompletion checkTask(@Param("checkerId")String checker_id,
                             @Param("checkerType")int checker_type,
                             @Param("date")String date,
                             @Param("type") int type,
                             @Param("time") String time);


    /**
     * TODO 暂时无用
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

    /**
     * 添加考勤任务
     * @param task 考勤任务
     * @param gradeId 班级id
     * @param termId 学期id
     * @param type 考勤类型
     * @return 插入的条数
     */
    void addTask(@Param("task")TaskInfo task,@Param("termId") Integer termId,
                 @Param("type") Integer type,@Param("gradeId")Integer gradeId
                 );

}
