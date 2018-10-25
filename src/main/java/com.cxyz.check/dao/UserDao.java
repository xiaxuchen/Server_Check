package com.cxyz.check.dao;

import com.cxyz.check.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /**
     * 通过用户id获取一个用户的信息
     * @param id 用户id
     * @return 用户实体
     */
    public User selectOne(@Param("id") String id,@Param("type")int type);

    /**
     * 通过班级id获取一个班的学生信息(学号，姓名，性别，照片路径)
     * @param grade 班级id
     * @return 返回班级所有学生的信息
     */
    public List<User> selectStusByGrade(@Param("grade") int grade);

    /**
     * 通过班级获取班级的班主任信息
     * @param grade 班级id
     * @return 班主任信息
     */
    public User selectTeaByGrade(@Param("grade") int grade);

    /**
     * 通过班级获取班主任id
     * @param grade 班级id
     * @return
     */
    public String selectTeaIDByGrade(@Param("grade") int grade);
    /**
     * 添加用户
     * @param users
     */
    public void addUsers(@Param("users")List<User> users,@Param("type") int type);
}
