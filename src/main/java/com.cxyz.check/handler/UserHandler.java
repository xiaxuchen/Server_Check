package com.cxyz.check.handler;

import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.enums.UserErrorEnum;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.PasswordErrorException;
import com.cxyz.check.exception.UserNotFoundException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.service.EnvirService;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.excel.POIUtil;
import com.cxyz.check.util.parse.GsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHandler {

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
     * 获取该班级所有学生的信息用于考勤
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


    @RequestMapping(value = "/addStus",
        method = RequestMethod.POST,
        produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<String>> addStus(@RequestParam MultipartFile file,
                                      @RequestParam int type,
                                      @RequestParam int gradeId)
    {
        CheckResult<List<String>> checkResult = new CheckResult<>();
        if(file.isEmpty())
        {
            checkResult.setError("文件为空!");
            return checkResult;
        }
        else {
            //解析excel
            try {
                List<List<String>> data = POIUtil.getBankListByExcel(file.getInputStream(), file.getOriginalFilename());
                List head = data.get(0);
                List<User> users = new ArrayList<>();
                User user;
                for(List list:data)
                {
                    user= new User();
                    StringBuilder builder = new StringBuilder();
                    int count = 0;
                    if(list == head)
                        continue;
                    user.setName((String) list.get(get(head,"姓名")));
                    user.setId((String) list.get(get(head,"学号")));
                    user.setSex((String) list.get(get(head,"性别")));
                    user.setPhone((String) list.get(get(head,"电话")));
                    if(user.getName().isEmpty())
                    {
                        builder.append("姓名不能为空\n");
                        count++;
                    }
                    if(user.getId().isEmpty())
                    {
                        builder.append("学号不能为空\n");
                        count++;
                    }
                    if(user.getSex().isEmpty())
                    {
                        builder.append("性别不能为空\n");
                        count++;
                    }

                    if(count == 3)
                        break;
                    else if(count>0)
                        throw new Exception(builder.toString());

                    users.add(user);
                }
                userService.addUser(users,type,gradeId);
                List<String> ids = new ArrayList<>();
                users.forEach(user1 -> ids.add(user1.getId()));
                checkResult.setSuccess(true);
                checkResult.setData(ids);
            } catch (Exception e) {
                e.printStackTrace();
                checkResult.setError("导入失败");
                return checkResult;
            }
        }
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


}
