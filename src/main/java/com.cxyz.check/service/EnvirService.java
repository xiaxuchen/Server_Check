package com.cxyz.check.service;

import com.cxyz.check.dto.AddTermDto;
import com.cxyz.check.dto.GradeDto;
import com.cxyz.check.exception.envir.UserImportedException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface EnvirService {

    /**
     * 添加学期
     * @param dto
     */
    void addTerm(AddTermDto dto);

    /**
     * 获取学院所有班级id和name
     * @param collegeId
     * @return
     */
    List<GradeDto> getCollegeGrades(Integer collegeId);

    /**
     * 是否可以导入学生信息信息
     * @param gradeId 班级id
     * @return
     */
    boolean isUserImportEnable(int gradeId) throws UserImportedException;

    /**
     * 是否可导入课程信息
     * @param gradeId 班级id
     * @return
     */
    boolean isLessonImportEnable(int gradeId);

    /**
     * 切换学院的学生信息导入状态
     * @param collegeId
     */
    void toggleUserImport(int collegeId);

    /**
     * 切换学院的课程信息导入状态
     * @param collegeId
     */
    void toggleLessonImport(int collegeId);

    /**
     * 学院是否开启用户导入
     * @param collegeId
     * @return
     */
    boolean isCollegeUserImportEnable(int collegeId);

    /**
     * 学院是否开启课程导入
     * @param collegeId
     * @return
     */
    boolean isCollegeLessonImportEnable(int collegeId);
}
