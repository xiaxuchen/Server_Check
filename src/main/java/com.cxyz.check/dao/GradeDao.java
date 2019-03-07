package com.cxyz.check.dao;

import com.cxyz.check.dto.GradeDto;
import com.cxyz.check.entity.Grade;

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

    /**
     * 通过id，type获取班级id
     * @param id
     * @param type
     * @return
     */
    Integer getGradeId(@Param("id") String id,@Param("type") int type);

    /**
     * 获取班主任id
     * @param id
     * @return
     */
    String getManagerId(@Param("id") Integer id);


    /**
     * 获取学院id
     * @return
     */
    Integer getCollegeId(@Param("gradeId") Integer gradeId);

    /**
     * 添加课程时，更新课程所在的学期
     * @param lessonTerm 课程的学期
     * @return
     */
    int addTasks(@Param("gradeId") int gradeId,@Param("lessonTerm")int lessonTerm);

    /**
     * 是否可导入学生信息,如果返回0则表示已导入,返回1为可导入,返回-1为未开启
     * @param gradeId 班级id
     * @return
     */
    int isStuImportEnable(@Param("gradeId") int gradeId);

    /**
     * 是否可导入课程,如果返回0则表示已导入,返回1为可导入,返回-1为未开启
     * @param gradeId 班级id
     * @param currentTerm 当前学期id
     * @return
     */
    int isLessonImportEnable(@Param("gradeId")int gradeId,@Param("currentTerm")int currentTerm);

    /**
     * 添加一个班级
     * @param grade 班级信息(必须要班级管理员对象，教室对象，学院对象，学院对象id不能为空)，添加完成将返回id在grade的id字段
     * @return 记录修改条数
     */
    int addGrade(@Param("grade") Grade grade);

    /**
     * 更新一个班级信息
     * @param grade 班级信息，必须有班级id
     * @return 记录修改条数
     */
    int updateGrade(@Param("grade") Grade grade);

    /**
     * 删除一个班级
     * @param id 班级id
     * @return 记录修改条数
     */
    int deleteGrade(@Param("id") int id);
}

