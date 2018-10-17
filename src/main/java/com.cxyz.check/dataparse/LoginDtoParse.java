package com.cxyz.check.dataparse;

import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.Student;
import com.cxyz.check.entity.Teacher;
import com.cxyz.check.entity.typevalue.UserType;

public class LoginDtoParse {

    /**
     * 成功返回老师数据时使用的方法
     * @param u
     */
    public static final LoginDto getLoginDto(Teacher u)
    {
        LoginDto loginDto = new LoginDto();
        loginDto.setType(UserType.TEACHER);
        loginDto.setName(u.getName());
        loginDto.setSex(u.getSex());
        loginDto.setPhone(u.getPhone());
        loginDto.setPhoto(u.getPhoto());
        loginDto.setCollegeId(u.getCollege().getId());
        loginDto.setCollegeName(u.getCollege().getName());
        loginDto.setPower(u.getPower());
        return loginDto;
    }


    /**
     * 成功返回学生时返回的方法
     * @param u
     */
    public static final LoginDto getLoginDto(Student u)
    {
        LoginDto loginDto = new LoginDto();
        loginDto.setType(UserType.STUDENT);
        loginDto.setName(u.getName());
        loginDto.setSex(u.getSex());
        loginDto.setPhone(u.getPhone());
        loginDto.setPhoto(u.getPhoto());
        loginDto.setGradeId(u.getGrade().getId());
        loginDto.setGradeName(u.getGrade().getName());
        loginDto.setCollegeName(u.getCollegeName());
        loginDto.setPower(u.getPower());
        return loginDto;
    }


}
