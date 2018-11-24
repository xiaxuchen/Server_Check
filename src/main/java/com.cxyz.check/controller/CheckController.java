package com.cxyz.check.controller;

import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.enums.UserErrorEnum;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.exception.task.NoTaskException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.exception.transaction.TransactionalException;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.parse.GsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RecordService recordService;

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
    public CheckResult<LoginDto> login(@RequestParam String id, @RequestParam
            String pwd, @RequestParam int type)
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
     * 检查是否有考勤任务
     * @param checkerId 考勤人id
     * @param checkerType 考勤人类型
     * @param type 考勤类型·
     * @return
     */
    @RequestMapping(value = "/checkTask",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<CheckTaskDto>
    checkTask(@RequestParam String checkerId,@RequestParam int checkerType
    ,@RequestParam int type)
    {
        try {
            CheckTaskDto taskDto = taskService.checkTask(checkerId, checkerType, type);
            return new CheckResult<CheckTaskDto>(taskDto);
        }catch (NoTaskException e)
        {
            return new CheckResult("当前暂无考勤任务");
        }
    }

    /**
     * 获取该班级所以学生的信息
     * @param grade 考勤班级
     * @return
     */
    @RequestMapping(value = "/gradeStus",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<GradeStusDto>>
    gradeStus(@RequestParam int grade)
    {
        try {
            List<GradeStusDto> gradeStusDtos = taskService.gradeStus(grade);
            return new CheckResult<List<GradeStusDto>>(gradeStusDtos);
        }catch (GradeNotFoundException e)
        {
            return new CheckResult(e.getMessage());
        }
    }

    @RequestMapping(value = "/commitCheck",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<String>
    commitCheck(@RequestParam String commitCheck)
    {
        CheckResult<String> stringCheckResult = new CheckResult<String>();

        try {
            CommitCheckDto commitCheckDto = (CommitCheckDto)
                    GsonUtil.fromJson(commitCheck, CommitCheckDto.class);
            taskService.commitCheck(commitCheckDto);
            stringCheckResult.setSuccess(true);
            stringCheckResult.setData("提交成功");
            return stringCheckResult;
        } catch (GsonException e) {
            e.printStackTrace();
            stringCheckResult.setData("提交数据异常");
            stringCheckResult.setSuccess(false);
            return stringCheckResult;
        }catch (CommitCheckFailException e)
        {
            e.printStackTrace();
            stringCheckResult.setData(e.getMessage()==null?
                    "提交失败":e.getMessage());
            stringCheckResult.setSuccess(false);
            return stringCheckResult;
        }
    }


    @RequestMapping(value = "/getGradeCheck",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<CheckRecordDto>
    getGradeCheck(@RequestParam String id,
                  @RequestParam int type,@RequestParam int grade)
    {
        return new CheckResult<CheckRecordDto>(recordService
                .getCheckRecord(id,type,grade));
    }


}
