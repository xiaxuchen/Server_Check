package com.cxyz.check.dao;

import com.cxyz.check.entity.Student;

import org.apache.ibatis.annotations.Param;

import java.util.List;

//测试成功
public interface StudentDao {

    /**
     * 通过学生学号获取学生信息
     * @param id 学生学号
     * @return 一个学生的信息
     */
    public Student getStuById(@Param("id") String id);

    /**
     * 通过班级id获取学生信息
     * @param grade 班级id
     * @return 一个班的学生信息
     */
    public List<Student> getStusByGrade(@Param("grade") int grade);
}
