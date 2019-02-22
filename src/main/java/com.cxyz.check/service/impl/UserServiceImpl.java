package com.cxyz.check.service.impl;

import com.cxyz.check.dao.EnvirDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.Grade;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.transaction.TransactionalException;
import com.cxyz.check.exception.user.ActiveException;
import com.cxyz.check.exception.user.AlterPwdException;
import com.cxyz.check.exception.user.PasswordErrorException;
import com.cxyz.check.exception.user.UserNotFoundException;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.automapper.AutoMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private EnvirDao envirDao;
    @Override
    public LoginDto login(String id, String password, int type) {
            User u = userDao.selectOne(id,type);
            if(u == null)
                throw new UserNotFoundException();

            if(u.getPwd().equals(password))
            {
                LoginDto loginDto = AutoMapper.mapping(u, new LoginDto());
                loginDto.setType(type);
                return loginDto;
            }

            throw new PasswordErrorException();
    }

   @Override
    public List<GradeStusDto> getGradeStus(int gradeId) {
        List<User> stus = userDao.selectStusByGrade(gradeId);
        //如果查询不到对象，返回空
        if (stus != null) {
            if (!stus.isEmpty()) {
                List<GradeStusDto> gradeStusDtos = AutoMapper.mappingList(stus, GradeStusDto.class);
                return gradeStusDtos;
            }
        }
        throw new GradeNotFoundException();
    }

    @Override
    public void addUser(List<User> users, int type,int gradeId) {
        //获取班级学院信息
        Grade userNeedById = envirDao.getUserNeedById(gradeId);
        userNeedById.setId(gradeId);
        //设置给用户
        for(User u:users)
        {
            u.setGrade(userNeedById);
            u.setCollege(userNeedById.getCollege());
        }
        //添加用户
        userDao.addUsers(users,type);
    }

    @Override
    public String getEmail(String id, Integer type) {
        return userDao.getEmail(id,type);
    }

    @Override
    public void alterPassword(String id, Integer type, String originPwd, String newPwd) {
        if(newPwd==null || 6>newPwd.length()||newPwd.length()>12)
            throw new AlterPwdException("密码长度应该为6-12位");
        int count = userDao.alterPassword(id,type,originPwd,newPwd);
        if(count == 0)
            throw new AlterPwdException("密码修改失败");
    }

    @Transactional
    @Override
    public void activeAccountPre(String id, Integer type, String newPwd, String mail, String acode) {
        final int count = userDao.activeAccountPre(id,type,newPwd,mail,acode);
        if(count == 0)
        {
            throw new ActiveException("账号未找到");
        }
        if(count !=1)
        {
            throw new TransactionalException("未知异常");
        }

    }

    @Transactional
    @Override
    public void activeAccount(Integer type, String acode) {
        final int count = userDao.activeAccount(type, acode);
        if(count == 0)
        {
            throw new ActiveException("无此验证信息");
        }
        if(count !=1)
        {
            throw new TransactionalException("未知异常");
        }
    }

    @Transactional
    @Override
    public void forgetPwd(String id,Integer type,String newPwd,String acode)
    {
        final int count = userDao.forgetPwd(id, type, newPwd, acode);
        if(count == 0)
        {
            throw new ActiveException("无此账号信息");
        }
        if(count !=1)
        {
            throw new TransactionalException("未知异常");
        }
    }

    @Transactional
    @Override
    public void confirmPwd(Integer type, String acode) {
        final int count = userDao.confirmPwd(type,acode);
        if(count == 0)
        {
            throw new ActiveException("无此验证信息");
        }
        if(count !=1)
        {
            throw new TransactionalException("未知异常");
        }
    }
}
