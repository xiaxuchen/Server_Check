package com.cxyz.check.dao;

import com.cxyz.check.dto.GradeLessonDto;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;

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

    /**
     * 获取班级的课程表
     * @param gradeId 班级id
     * @param termId 学期id
     * @return
     */
    List<TaskInfo> getSubjects(@Param("gradeId") int gradeId,@Param("termId") int termId);

    /**
     * 获取老师在此班级的考勤汇总
     * @param lessonId 课程id
     * @return
     */
    List<TaskInfo> getTasks(@Param("lessonId") Integer lessonId);

    /**
     * 通过发起人或老师id获取所教班级的课程信息
     * @param sponsorId 发起人id
     * @param sponsorType 发起人类型
     * @param termId
     * @return
     */
    List<GradeLessonDto> getGradeTasks(@Param("sponsorId") String sponsorId, @Param("sponsorType") Integer sponsorType, @Param("termId")Integer termId);

}
