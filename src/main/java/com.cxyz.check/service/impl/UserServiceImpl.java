package com.cxyz.check.service.impl;

import com.cxyz.check.dao.StudentDao;
import com.cxyz.check.dao.TeacherDao;
import com.cxyz.check.dataparse.LoginDtoParse;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.Student;
import com.cxyz.check.entity.Teacher;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public LoginDto login(String id, String password, int type) {
        if(type == UserType.STUDENT) {
            Student stu = studentDao.getStuById(id);
            if(stu == null)
                throw new UserNotFoundException();
            if(stu.getPwd().equals(password))
            {
                return LoginDtoParse.getLoginDto(stu);
            }
        }else if(type == UserType.TEACHER)
        {
            Teacher tea = teacherDao.getTeaById(id);
            if(tea == null)
            if(tea.getPwd().equals(password))
            {
                return LoginDtoParse.getLoginDto(tea);
            }
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
