package com.cxyz.check.service;

import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.transaction.TransactionalException;
import com.cxyz.check.exception.user.ActiveException;
import com.cxyz.check.exception.user.PasswordErrorException;
import com.cxyz.check.exception.user.UserException;
import com.cxyz.check.exception.user.UserNotFoundException;

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

    /**
     * 获取用户email
     * @param id 用户id
     * @param type 用户类型
     */
    String getEmail(String id,Integer type);

    /**
     * 修改密码
     * @param id 用户名
     * @param type 用户类型
     * @param originPwd 原密码
     * @param newPwd 新密码
     */
    void alterPassword(String id,Integer type,String originPwd,String newPwd);

    /**
     * 准备激活账号
     * @param id 用户id
     * @param type 用户类型
     * @param newPwd 新密码
     * @param mail 邮箱
     * @param acode 激活码
     * @throws ActiveException 修改记录数为0抛出
     * @throws TransactionalException 修改记录不为0不为1抛出
     */
    void activeAccountPre(String id,Integer type,String newPwd,String mail,String acode) throws ActiveException, TransactionalException;

    /**
     * 激活账号
     * @param type 账号类型
     * @param acode 激活码
     * @throws ActiveException 修改记录数为0抛出
     * @throws TransactionalException 修改记录不为0不为1抛出
     */
    void activeAccount(Integer type,String acode) throws ActiveException,TransactionalException;

    /**
     * 忘记密码
     * @param id 用户id
     * @param type 用户类型
     * @param newPwd 新密码
     * @param acode 验证码
     */
    void forgetPwd(String id,Integer type,String newPwd,String acode) throws ActiveException,TransactionalException;
    /**
     * 完成忘记密码
     * @param type 用户类型
     * @param acode 验证码
     */
    void confirmPwd(Integer type,String acode) throws ActiveException,TransactionalException;
}
