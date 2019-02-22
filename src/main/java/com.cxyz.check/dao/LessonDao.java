package com.cxyz.check.dao;

import com.cxyz.check.entity.Lesson;
import com.cxyz.check.entity.TaskInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LessonDao {

    /**
     * 添加课程
     * @param lesson 课程信息
     * @return
     */
    int addLesson(@Param("lesson") TaskInfo lesson);

    /**
     * 获取课程信息
     * @return
     */
    Lesson getLesson(@Param("sponsorId")String sponsorId,@Param("sponsorType")Integer sponsorType,@Param("id") Integer lessonId);

    /**
     * 更新课程编号
     * @param num 课程编号
     * @return
     */
    int updateLessonNum(@Param("id")Integer id,@Param("num")String num);

    /**
     * 更新课程房间
     * @param room 房间号
     * @return
     */
    int updateLessonRoom(@Param("id")Integer id,@Param("room") Integer room);
}
