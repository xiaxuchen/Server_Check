package com.cxyz.check.service;

import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserException;
import com.cxyz.check.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     * @param id 用户id
     * @param password 用户密码
     * @param type 用户类型
     * @return 用户的dto
     * @throws UserNotFoundException 在数据库中找不到相应id的用户时抛出
     * @throws PasswordErrorException 密码错误时抛出
     * @throws UserException
     */
    LoginDto login(String id, String password, int type)
    throws UserNotFoundException, PasswordErrorException, UserException;

    /**
     * 考勤时需要班级的学生列表
     * @param gradeId 班级id
     * @return 一个班的学生用户信息
     * @throws GradeNotFoundException 当在数据库中查询不到任何记录时抛出
     */
    List<GradeStusDto> getGradeStus(int gradeId)
    throws GradeNotFoundException;

    /**
     * 添加用户
     * @param users 用户信息
     * @param type 用户类型
     * @param gradeId 班级id
     */
    void addUser(List<User> users,int type,int gradeId);
}
