package com.cxyz.check.service;

import com.cxyz.check.custom.TaskInfoCustom;
import com.cxyz.check.entity.Lesson;
import com.cxyz.check.entity.TaskInfo;

import java.util.List;

public interface LessonService {

    /**
     * 添加课程
     */
    void addLesson(List<TaskInfo> lessons,Integer type,Integer gradeId);

    /**
     * 更新课程编号
     * @param id 课程id
     * @param num 课程编号
     */
    void updateLessonNum(Integer id,String num);
}
