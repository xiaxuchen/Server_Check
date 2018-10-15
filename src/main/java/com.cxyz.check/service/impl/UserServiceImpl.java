package com.cxyz.check.service.impl;

import com.cxyz.check.dao.StudentDao;
import com.cxyz.check.dao.TeacherDao;
import com.cxyz.check.dto.UserDto;
import com.cxyz.check.entity.Student;
import com.cxyz.check.entity.Teacher;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public UserDto login(String id, String password, int type) {
        if(type == UserDto.STUDENT) {
            Student stu = studentDao.getStuById(id);
            if(stu == null)
                throw new UserNotFoundException();
            if(stu.getPwd().equals(password))
            {
                return new UserDto(stu);
            }
        }else if(type == UserDto.TEACHER)
        {
            Teacher tea = teacherDao.getTeaById(id);
            if(tea == null)
            if(tea.getPwd().equals(password))
            {
                return new UserDto(tea);
            }
        }
        throw new PasswordErrorException();
    }

    @Override
    public List<UserDto> getGradeStus(int gradeId) {
        List<Student> stus = studentDao.getStusByGrade(gradeId);
        //如果查询不到对象，返回空
        if (stus != null) {
            if (!stus.isEmpty()) {
                List<UserDto> userDtos = new ArrayList<UserDto>();
                UserDto dto = null;
                for (Student stu : stus) {
                    dto = new UserDto(stu);
                    userDtos.add(dto);
                }
                return userDtos;
            }
        }
        throw new GradeNotFoundException();
    }
}
