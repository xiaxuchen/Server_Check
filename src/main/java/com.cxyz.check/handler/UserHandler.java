package com.cxyz.check.controller;

import com.cxyz.check.dao.EnvirDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.AddTermDto;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.User;
import com.cxyz.check.enums.UserErrorEnum;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.exception.envir.TermExistException;
import com.cxyz.check.exception.task.NoTaskException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.exception.transaction.TransactionalException;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.service.EnvirService;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.excel.POIUtil;
import com.cxyz.check.util.parse.GsonUtil;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private EnvirService envirService;

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


    @RequestMapping(value = "/upload",
        method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<String> upload(@RequestParam MultipartFile file,
                                      @RequestParam int type,
                                      @RequestParam int gradeId)
    {
        CheckResult<String> checkResult = new CheckResult<>();
        if(file.isEmpty())
        {
            checkResult.setError("导入失败");
            return checkResult;
        }
        else {
            //解析excel
            try {
                List<List<Object>> data = POIUtil.getBankListByExcel(file.getInputStream(), file.getOriginalFilename());
                List head = data.get(0);
                List<User> users = new ArrayList<>();
                User user = null;
                for(List list:data)
                {
                    user= new User();
                    if(list == head)
                        continue;
                    user.setName((String) list.get(get(head,"姓名")));
                    user.setId((String) list.get(get(head,"学号")));
                    user.setSex((String) list.get(get(head,"性别")));
                    user.setPhone((String) list.get(get(head,"电话")));
                    users.add(user);
                }
                userService.addUser(users,type,gradeId);
            } catch (Exception e) {
                e.printStackTrace();
                checkResult.setError("导入失败");
                return checkResult;
            }
        }
        checkResult.setSuccess(true);
        checkResult.setData("导入成功");
        return checkResult;
    }

    /**
     * 通过字段名获取索引
     * @param head
     * @param info
     * @return
     */
    private int get(List head,String info)
    {
        int index = 0;
        for(Object obj:head)
        {
            if(obj.toString().trim().equals(info))
                return index;
            index++;
        }
        throw new RuntimeException();
    }


    @RequestMapping(value = "/addTerm",
        method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<String> addTerm(@RequestBody AddTermDto dto)
    {
        CheckResult<String> checkResult = new CheckResult<>();
        try {
            envirService.addTerm(dto);
            checkResult.setSuccess(true);
            checkResult.setData("提交成功!");
        }catch (TermExistException e)
        {
            checkResult.setError("当前学期已存在!");
        }

        return checkResult;
    }

}
