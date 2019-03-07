package com.cxyz.check.handler;

import com.cxyz.check.dto.AddTermDto;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.GradeDto;
import com.cxyz.check.entity.College;
import com.cxyz.check.entity.School;
import com.cxyz.check.entity.Term;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.envir.LessonImportedException;
import com.cxyz.check.exception.envir.TermExistException;
import com.cxyz.check.exception.envir.UserImportedException;
import com.cxyz.check.nexception.DataConflictException;
import com.cxyz.check.nexception.DataNotFoundException;
import com.cxyz.check.nexception.ParameterInvalidException;
import com.cxyz.check.service.EnvirService;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.automapper.annotation.Path;
import com.cxyz.check.util.date.Date;
import com.cxyz.check.util.pinyin.PinYinUtil;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import sun.security.util.Length;

@Controller
@RequestMapping("/envir")
public class EnvirHandler {

    @Autowired
    private EnvirService envirService;

    /**
     * 添加学期信息
     * @param dto 学期
     * @return 提交结果
     */
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

    @RequestMapping(value = "/uploadBugs",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<String> uploadBugs(@RequestParam MultipartFile file, HttpServletRequest request) {
        CheckResult<String> checkResult = new CheckResult<>();
        //目录名称
        String dirname = request.getServletContext().getRealPath("/WEB-INF")
                + "/" + Date.fromUDate(new java.util.Date()).toString();


        File dir = new File(dirname);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String originName = file.getOriginalFilename();
        File path = new File(dir.getAbsolutePath(), UUID.randomUUID().toString()+
                originName.substring(originName.lastIndexOf(".")));
        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(file.getBytes());
        } catch (Exception e)
        {
            checkResult.setError("上传失败!");
            return checkResult;
        }
        checkResult.setSuccess(true);
        checkResult.setData("上传成功!");
        return checkResult;
    }

    /**
     * url:/envir/getCollegeGrades
     * 获取学院的班级信息
     * @param collegeId 学院id
     * @return
     */
    @RequestMapping(value="getCollegeGrades",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"}
    )
    @ResponseBody
    public CheckResult<List<GradeDto>> getCollegeGrades(@RequestParam Integer collegeId)
    {
        return new CheckResult<>(envirService.getCollegeGrades(collegeId));
    }


    /**
     * 是否可导入用户信息
     * @param gradeId
     * @return
     */
    @RequestMapping(value="isUserImportEnable",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"}
    )
    @ResponseBody
    public CheckResult<Boolean> isUserImportEnable(@RequestParam int gradeId)
    {
        CheckResult<Boolean> cr = new CheckResult<>();
        try {
            cr.setData(envirService.isUserImportEnable(gradeId));
        } catch (UserImportedException e) {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }


    /**
     * 是否可导入课程信息
     * @param gradeId
     * @return
     */
    @RequestMapping(value="isLessonImportEnable",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"}
    )
    @ResponseBody
    public CheckResult<Boolean> isLessonImportEnable(@RequestParam int gradeId)
    {
        CheckResult<Boolean> cr = new CheckResult<>();
        try {
            cr.setData(envirService.isLessonImportEnable(gradeId));
        } catch (LessonImportedException e) {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }

    /**
     * 切换用户导入状态
     * @param collegeId 学院id
     * @return
     */
    @RequestMapping(value="toggleUserImport",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"}
    )
    @ResponseBody
    public CheckResult<Boolean> toggleUserImport(@RequestParam int collegeId)
    {
        CheckResult<Boolean> cr = new CheckResult<>();
        try {
            envirService.toggleUserImport(collegeId);
            cr.setData(envirService.isCollegeUserImportEnable(collegeId));
        }catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }

    /**
     * 是否可导入课程信息
     * @param collegeId
     * @return
     */
    @RequestMapping(value="toggleLessonImport",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"}
    )
    @ResponseBody
    public CheckResult<Boolean> toggleLessonImport(@RequestParam int collegeId)
    {
        CheckResult<Boolean> cr = new CheckResult<>();
        try {
            envirService.toggleLessonImport(collegeId);
            cr.setData(envirService.isCollegeLessonImportEnable(collegeId));
        }catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }


    /**
     * 是否可导入用户信息
     * @param gradeId
     * @return
     */
    @RequestMapping(value="isCollegeUserImportEnable",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"}
    )
    @ResponseBody
    public CheckResult<Boolean> isCollegeUserImportEnable(@RequestParam int gradeId)
    {
        CheckResult<Boolean> cr = new CheckResult<>();
        try {
            cr.setData(envirService.isCollegeUserImportEnable(gradeId));
        } catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }


    /**
     * 是否可导入课程信息
     * @param gradeId
     * @return
     */
    @RequestMapping(value="isCollegeLessonImportEnable",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"}
    )
    @ResponseBody
    public CheckResult<Boolean> isCollegeLessonImportEnable(@RequestParam int gradeId)
    {
        CheckResult<Boolean> cr = new CheckResult<>();
        try {
            cr.setData(envirService.isCollegeLessonImportEnable(gradeId));
        }catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }


    /**
     * 创建学校
     * @param name 学校名称
     * @return 学校id
     */
    @RequestMapping(value = "/school/{name}",
        method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public CheckResult<Integer> addSchool(@PathVariable String name, @RequestParam(required = false) String managerId, @RequestParam(required = false) String pwd)
    {
        CheckResult<Integer> cr = new CheckResult<>();
        if (name == null)
        {
            cr.setError("学校名称不能为空");
            return cr;
        }
        if(pwd == null||pwd.isEmpty())
            pwd = "123456";
        if(pwd.length() < 6)
        {
            cr.setError("密码长度不能小于6");
            return cr;
        }
        School school = new School(name);
        if(managerId == null)
        {

            String firstSpelling = PinYinUtil.getFirstSpelling(name);
            if(firstSpelling.length() > 10)
                firstSpelling = firstSpelling.substring(0,10);
            else{
                int length = 10 - firstSpelling.length();
                Random random = new Random();
                for(int i = 0;i< length;i++)
                {
                    firstSpelling+=(random.nextInt(8)+1);
                }
            }
            school.setManagerId(firstSpelling);
        }else {
            school.setManagerId(managerId);
        }
        school.setManagerPwd(pwd);
        try {
            envirService.addSchool(school);
            cr.setData(school.getId());
        }catch (ParameterInvalidException e)
        {
            cr.setError(e.getMessage());
        }
        return cr;
    }


    /**
     * 更新学校信息
     * @param id 学校id 标识学校
     * @param name 学校名
     * @param pwd 密码
     * @param termId 当前学期id
     * @return
     */
    @RequestMapping(value = "/school/{id}",
            method = RequestMethod.PATCH,
            produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public CheckResult updateSchool(@PathVariable Integer id, @RequestParam(required = false) String name, @RequestParam(required = false) String pwd, @RequestParam(required = false) Integer termId)
    {
        CheckResult cr = new CheckResult();
        if(name == null && pwd == null && termId == null)
        {
            cr.setError("未发现需要更新的信息");
            return cr;
        }
        School school = new School(id);
        school.setName(name);
        if(pwd != null)
            school.setManagerPwd(pwd);
        if(termId != null && termId == -1)
            school.setTerm(new Term());
        else
            school.setTerm(new Term(termId));
        try {
            envirService.updateSchool(school);
            cr.setSuccess(true);
        } catch (ParameterInvalidException e) {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (DataConflictException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }
        return cr;
    }

    /**
     * 通过学校id删除学校
     * @param id 学校id
     * @return
     */
    @RequestMapping(value = "/school/{id}",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public CheckResult deleteSchool(@PathVariable int id)
    {
        CheckResult cr = new CheckResult();
        try {
            envirService.deleteSchool(id);
            cr.setSuccess(true);
        }catch (ParameterInvalidException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器内部异常");
        }
        return cr;
    }

    /**
     * 添加学院信息
     * @param name 学院名称
     * @param schoolId 学校id
     * @return
     */
    @RequestMapping(value = "/college/{name}/school/{schoolId}",
        method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public CheckResult<Integer> addCollege(@PathVariable String name,@PathVariable Integer schoolId)
    {
        CheckResult<Integer> cr = new CheckResult<>();
        if(name.isEmpty())
        {
            cr.setError("学院名称不能为空");
            return cr;
        }
        College college = new College();
        college.setName(name);
        try {
            envirService.addCollege(college,schoolId);
            cr.setData(college.getId());
        }catch (ParameterInvalidException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器内部异常");
        }
        return cr;
    }

    /**
     * 更新学院细心
     * @param id 学院id 必填 标识更新的是哪个学院
     * @param name 学院名称
     * @param managerId 管理员id
     * @return
     */
    @RequestMapping(value = "/college/{id}",
        method = RequestMethod.PATCH,
        produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public CheckResult updateCollege(@PathVariable Integer id,@RequestParam(required = false) String name,@RequestParam(required = false) String managerId)
    {
        CheckResult cr = new CheckResult();
        College college = new College();
        college.setId(id);
        if(name == null && managerId == null)
        {
            cr.setError("未发现更新数据");
            return cr;
        }
        if(!(name == null || name.isEmpty()))
            college.setName(name);
        if(managerId != null)
        {
            if(managerId.equals(""))
                college.setManager(new User());
            else
                college.setManager(new User(managerId));
        }
        try {
            envirService.updateCollege(college);
            cr.setSuccess(true);
        }catch (ParameterInvalidException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (DataConflictException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器内部异常");
        }
        return cr;
    }

    /**
     * 通过id删除学院
     * @param id 学院id
     * @return
     */
    @RequestMapping(value = "/college/{id}",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public CheckResult deleteCollege(@PathVariable Integer id)
    {
        CheckResult cr = new CheckResult();
        try {
            envirService.deleteCollege(id);
        }catch (ParameterInvalidException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器内部异常");
        }
        return cr;
    }


    /**
     * 获取所有学校的信息
     * @param field 排序字段
     * @param isAsc 是否升序
     * @return 学校列表
     */
    @RequestMapping(value = "/schools",
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CheckResult<List<School>> getSchools(@RequestParam(required = false) String field,@RequestParam(defaultValue = "false") boolean isAsc)
    {
        CheckResult<List<School>> cr = new CheckResult<>();
        try {
            cr.setData(envirService.getSchools(field,isAsc));
        }catch (Exception e)
        {
            cr.setError("服务器内部异常");
        }
        return cr;
    }

    /**
     * 学校管理员登录
     * @param id 管理员id
     * @param pwd 密码
     * @return 通过isSuccess获取是否成功登录
     */
    @RequestMapping(value = "/school/manager/{id}",
        method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8"
    )
    @ResponseBody
    public CheckResult schoolManagerLogin(@PathVariable("id")String id,@RequestParam("pwd")String pwd)
    {
        CheckResult cr = new CheckResult();
        try {
            envirService.schoolManagerLogin(id,pwd);
            cr.setSuccess(true);
        }catch (DataNotFoundException e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器内部异常");
        }
        return cr;
    }
}
