package com.cxyz.check.handler;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.user.ActiveException;
import com.cxyz.check.exception.user.AlterPwdException;
import com.cxyz.check.service.EnvirService;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.service.UserService;
import com.cxyz.check.shiro.token.UserToken;
import com.cxyz.check.util.excel.POIUtil;
import com.cxyz.check.util.mail.MailUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
            Subject subject = SecurityUtils.getSubject();
            UserToken token = new UserToken(id, pwd, type);
            subject.logout();
            subject.login(token);
            login = token.getLoginDto();
        }catch (UnknownAccountException e)
        {
            return new CheckResult<>("用户不存在");
        }catch (IncorrectCredentialsException e)
        {
            return new CheckResult<>("密码错误");
        }catch (AuthenticationException e)
        {
            return new CheckResult<>("服务器内部异常");
        }
        return  new CheckResult<>(login);
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

    @RequestMapping(value = "/alterPwd",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<String> alterPwd(@RequestParam String id,@RequestParam Integer type,@RequestParam
            String originPwd,@RequestParam String newPwd)
    {
        CheckResult<String> cr = new CheckResult<>();
        try {
            userService.alterPassword(id,type,originPwd,newPwd);
            cr.setData("修改成功");
        }catch (AlterPwdException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器异常");
        }
        return cr;
    }

    /**
     * 验证密码
     * @param request
     * @param type 用户类型
     * @param acode 激活码
     * @return
     */
    @RequestMapping(value = "/confirmPwd/{type}/{acode}")
    public String confirmPwd(HttpServletRequest request, @PathVariable Integer type, @PathVariable String acode)
    {
        try {
            userService.confirmPwd(type,acode);
            request.setAttribute("msg","验证成功,密码已修改");
        }catch (ActiveException e)
        {
            e.printStackTrace();
            request.setAttribute("msg","验证失败:"+e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("msg","验证失败:未知错误");
        }
        return "/active/msg";
    }



    @RequestMapping(value = "/forgetPwd",
        produces = {"application/json;charset=utf-8"}
    )
    /**
     * 忘记密码
     * @param id
     * @param type
     * @param newPwd
     * @return
     */
    @ResponseBody
    public CheckResult forgetPwd(HttpServletRequest request,@RequestParam String id,@RequestParam Integer type,@RequestParam
            String newPwd,@RequestParam(required = false) String mail)
    {
        CheckResult cr = new CheckResult();
        if(mail == null)
            mail = userService.getEmail(id,type);
        if(mail == null)
        {
            cr.setError("非法操作,账号未绑定邮箱!");
            return cr;
        }
        String schema = request.getScheme();
        int port = request.getServerPort();
        System.out.println(schema);
        System.out.println(port);
        final String acode = UUID.randomUUID().toString().replace("-", "");
        try {
            userService.forgetPwd(id,type,newPwd,acode);
            MailUtil.sendEmail(mail,"点到为止账号改密验证",msg ->
            {
                final StringBuffer messageText=new StringBuffer();//内容以html格式发送,防止被当成垃圾邮件
                messageText.append("<h2>Hi,您好,这是点到为止的改密邮件</h2></br>");
                messageText.append("<a href='").append(schema).append("://").append(InetAddress.getLocalHost().getHostAddress())
                        .append(":").append(port).append("/user/confirmPwd/")
                        .append(type).append("/").append(acode).append("'").append(">点此确认修改</a>");
                try {
                    msg.setContent(messageText.toString(),"text/html;charset=utf-8");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            cr.setSuccess(true);
        }catch (ActiveException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("未知错误");
        }

        return cr;
    }



    /**
     *  激活账号的准备工作
     * @param id 用户id
     * @param type 用户类型
     * @param mail 邮箱
     * @param newPwd 新密码
     */
    @RequestMapping(value = "/activeAccountPre",
            produces = {"application/json;charset=utf-8"}
    )
    @ResponseBody
    public CheckResult activeAccountPre(HttpServletRequest request,@RequestParam String id,@RequestParam Integer type,@RequestParam String mail,@RequestParam String newPwd)
    {
        CheckResult cr = new CheckResult();
        String schema = request.getScheme();
        int port = request.getServerPort();
        System.out.println(schema);
        System.out.println(port);
        final String acode = UUID.randomUUID().toString().replace("-", "");
        try {
            userService.activeAccountPre(id,type,newPwd,mail,acode);
            MailUtil.sendEmail(mail,"点到为止账号激活",msg ->
            {
                final StringBuffer messageText=new StringBuffer();//内容以html格式发送,防止被当成垃圾邮件
                messageText.append("<h2>Hi,您好,这是点到为止的账号激活邮件</h2></br>");
                messageText.append("<a href='").append(schema).append("://").append(InetAddress.getLocalHost().getHostAddress())
                        .append(":").append(port).append("/user/activeAccount/")
                        .append(type).append("/").append(acode).append("'").append(">点此激活</a>");
                try {
                    msg.setContent(messageText.toString(),"text/html;charset=utf-8");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            cr.setSuccess(true);
        }catch (ActiveException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("未知错误");
        }

        return cr;
    }

    /**
     * 激活账号
     * @param request
     * @param type 用户类型
     * @param acode 激活码
     * @return
     */
    @RequestMapping(value = "/activeAccount/{type}/{acode}")
    public String activeAccount(HttpServletRequest request, @PathVariable Integer type, @PathVariable String acode)
    {
        try {
            userService.activeAccount(type,acode);
            request.setAttribute("msg","账号激活成功");
        }catch (ActiveException e)
        {
            e.printStackTrace();
            request.setAttribute("msg","激活失败"+e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("msg","激活失败,未知错误");
        }
        return "/active/msg";
    }

}
