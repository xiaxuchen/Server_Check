package com.cxyz.check.controller;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.enums.UserErrorEnum;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public CheckResult<LoginDto> login(@RequestParam("id") String id, @RequestParam("pwd")
            String pwd, @RequestParam("type") int type)
    {
        LoginDto login = null;
        try {
           login = userService.login(id, pwd, type);
        }catch (UserNotFoundException e)
        {
            return new CheckResult<LoginDto>(UserErrorEnum.USERNOTFOUND.getMsg());
        }catch (PasswordErrorException e)
        {
            return new CheckResult<LoginDto>(UserErrorEnum.PASSWORDERROR.getMsg());
        }catch (Exception e)
        {
            return new CheckResult<LoginDto>(UserErrorEnum.INNERERROR.getMsg());
        }
        return  new CheckResult<LoginDto>(login);
    }

    /**
     * 通过班级id获取学生信息
     * @param gradeId 班级id
     * @return 一个班的学生信息
     */
 /*   @RequestMapping(value = "/getGradeStus",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<LoginDto>> getGradleStus(@RequestParam("grade") int gradeId)
    {
        try {
            List<LoginDto> gradeStus = userService.getGradeStus(gradeId);
            return new CheckResult<List<LoginDto>>(gradeStus);
        }catch (GradeNotFoundException e)
        {
            return new CheckResult<List<LoginDto>>(GradeErrorEnum.GRADENOTFOUND.getMsg());
        }catch (Exception e)
        {
            return new CheckResult<List<LoginDto>>(GradeErrorEnum.INNERERROR.getMsg());
        }
    }*/
}
