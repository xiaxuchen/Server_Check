package com.cxyz.check.dao;

import com.cxyz.check.dto.GradeDto;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeDao {

    /**
     * 通过班级id获取所属学校id
     * @return 所属学校id
     */
    Integer getGradeSchoolId(@Param("gradeId")Integer gradeId);

    /**
     * 获取一个学院的班级信息
     * @param collegeId 学院id
     * @return
     */
    List<GradeDto> getCollegeGrades(@Param("collegeId") Integer collegeId);

    /**
     * 获取班级名称
     * @param gradeId 班级id
     * @return
     */
    String getGradeName(@Param("gradeId")Integer gradeId);
}
