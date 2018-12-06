package com.cxyz.check.service.impl;

import com.cxyz.check.dao.EnvirDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.Grade;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.automapper.AutoMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
