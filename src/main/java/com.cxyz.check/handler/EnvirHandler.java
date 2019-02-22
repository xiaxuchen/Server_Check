package com.cxyz.check.handler;

import com.cxyz.check.dto.AddTermDto;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.GradeDto;
import com.cxyz.check.exception.envir.LessonImportedException;
import com.cxyz.check.exception.envir.TermExistException;
import com.cxyz.check.exception.envir.UserImportedException;
import com.cxyz.check.service.EnvirService;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.service.UserService;
import com.cxyz.check.util.date.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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



}
