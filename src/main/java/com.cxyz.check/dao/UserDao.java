package com.cxyz.check.dao;

import com.cxyz.check.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /**
     * 获取所属学校
     * @param id
     * @param type
     * @return
     */
    Integer getSchoolId(@Param("id") String id, @Param("type") Integer type);

    /**
     * 通过id和用户类型获取用户姓名
     * @param id 用户id
     * @param type 用户类型
     * @return
     */
    String getName(@Param("id") String id, @Param("type") int type);

    /**
     * 通过用户id获取一个用户的信息
     * @param id 用户id
     * @return 用户实体
     */
    User selectOne(@Param("id") String id, @Param("type") int type);

    /**
     * 通过班级id获取一个班的学生信息(学号，姓名，性别，照片路径)
     * @param grade 班级id
     * @return 返回班级所有学生的信息
     */
    List<User> selectStusByGrade(@Param("grade") int grade);

    /**
     * 通过班级获取班级的班主任信息
     * @param grade 班级id
     * @return 班主任信息
     */
    User selectTeaByGrade(@Param("grade") int grade);

    /**
     * 通过班级获取班主任id
     * @param grade 班级id
     * @return
     */
    String selectTeaIDByGrade(@Param("grade") int grade);
    /**
     * 添加用户
     * @param users
     */
    void addUsers(@Param("users") List<User> users, @Param("type") int type);

    /**
     * 通过学生权限获取学生信息
     * @param gradeId
     * @param power
     */
    User findStuByPower(@Param("gradeId") Integer gradeId,@Param("power") Integer power);

    /**
     * 获取班级人数
     * @param gradeId 班级id
     * @return
     */
    int gradeStuCount(@Param("gradeId")Integer gradeId);

    /**
     * 获取学院中权限为power的老师的id
     * @param collegeId 学院id
     * @param power 权限
     * @return
     */
    List<String>  getTeaIds(@Param("collegeId") Integer collegeId,
                            @Param("power") Integer power);

    /**
     * 获取邮箱
     * @param id 用户id
     * @param type 用户类型
     * @return
     */
    String getEmail(@Param("id") String id,@Param("type") Integer type);


    /**
     * 修改密码
     * @param id 用户名
     * @param type 用户类型
     * @param originPwd 原密码
     * @param newPwd 新密码
     * @return
     */
    int alterPassword(@Param("id")String id,@Param("type")Integer type,@Param("originPwd")String originPwd,@Param("newPwd")String newPwd);

    /**
     * 激活账号准备工作
     * @param id 用户id
     * @param type 用户类型
     * @param newPwd 新密码
     * @param mail 邮箱
     * @param acode 激活码
     * @return
     */
    int activeAccountPre(@Param("id")String id,@Param("type")Integer type,@Param("newPwd")String newPwd,@Param("mail")String mail,@Param("acode")String acode);

    /**
     * 激活账号
     * @param type 用户类型
     * @param acode 激活码
     * @return
     */
    int activeAccount(@Param("type")Integer type,@Param("acode")String acode);

    /**
     * 激活忘记密码修改
     * @param type 用户类型
     * @param acode 激活码
     * @return
     */
    int confirmPwd(@Param("type")Integer type,@Param("acode")String acode);

    /**
     * 忘记密码进行修改
     * @param id 用户id
     * @param type 用户类型
     * @param newPwd 新密码
     * @param acode 验证码
     * @return
     */
    int forgetPwd(@Param("id")String id,@Param("type")Integer type,@Param("newPwd")String newPwd,@Param("acode")String acode);
}
