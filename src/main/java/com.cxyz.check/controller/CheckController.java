package com.cxyz.check.controller;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.UserDto;
import com.cxyz.check.enums.GradeErrorEnum;
import com.cxyz.check.enums.UserErrorEnum;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private UserService userService;

    /**
     * 通过用户输入的用户id、密码和用户类型进行登陆
     * @param id 用户id
     * @param pwd 密码
     * @param type 用户类型
     * @return
     */
    @RequestMapping(value = "/login",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<UserDto> login(@RequestParam("id") String id,@RequestParam("pwd")
            String pwd,@RequestParam("type") int type)
    {
        UserDto login = null;
        try {
           login = userService.login(id, pwd, type);
        }catch (UserNotFoundException e)
        {
            return new CheckResult<UserDto>(UserErrorEnum.USERNOTFOUND.getMsg());
        }catch (PasswordErrorException e)
        {
            return new CheckResult<UserDto>(UserErrorEnum.PASSWORDERROR.getMsg());
        }catch (Exception e)
        {
            return new CheckResult<UserDto>(UserErrorEnum.INNERERROR.getMsg());
        }
        return  new CheckResult<UserDto>(login);
    }

    /**
     * 通过班级id获取学生信息
     * @param gradeId 班级id
     * @return 一个班的学生信息
     */
    @RequestMapping(value = "/getGradeStus",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<UserDto>> getGradleStus(@RequestParam("grade") int gradeId)
    {
        try {
            List<UserDto> gradeStus = userService.getGradeStus(gradeId);
            return new CheckResult<List<UserDto>>(gradeStus);
        }catch (GradeNotFoundException e)
        {
            return new CheckResult<List<UserDto>>(GradeErrorEnum.GRADENOTFOUND.getMsg());
        }catch (Exception e)
        {
            return new CheckResult<List<UserDto>>(GradeErrorEnum.INNERERROR.getMsg());
        }
    }
}
