package com.cxyz.check.service.impl;

import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.automapper.AutoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public LoginDto login(String id, String password, int type) {
            User u = userDao.selectOne(id,type);
            if(u == null)
                throw new UserNotFoundException();

            if(u.getPwd().equals(password))
            {
                return AutoMapper.mapping(u,new LoginDto());
            }

            throw new PasswordErrorException();
    }

   /* @Override
    public List<LoginDto> getGradeStus(int gradeId) {
        List<Student> stus = studentDao.getStusByGrade(gradeId);
        //如果查询不到对象，返回空
        if (stus != null) {
            if (!stus.isEmpty()) {
                List<LoginDto> userDtos = new ArrayList<LoginDto>();
                LoginDto dto = null;
                for (Student stu : stus) {
                    dto = new LoginDto(stu);
                    userDtos.add(dto);
                }
                return userDtos;
            }
        }
        throw new GradeNotFoundException();
    }*/
}
