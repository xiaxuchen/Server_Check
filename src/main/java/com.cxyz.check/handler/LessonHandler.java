package com.cxyz.check.handler;

import com.cxyz.check.custom.TaskInfoCustom;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.entity.ClassRoom;
import com.cxyz.check.entity.Grade;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Term;
import com.cxyz.check.entity.Times;
import com.cxyz.check.entity.User;
import com.cxyz.check.service.LessonService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.excel.POIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lesson")
public class LessonHandler {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private TaskService taskService;


    /**
     * 添加课程信息并生成考勤任务
     * @param //file 上传的excel文件
     * @param type 课程类型
     * @param gradeId 班级id
     * @return
     */
    @RequestMapping(value = "/addLessons",
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public CheckResult<String> addLessons(@RequestParam Integer type,
                                          @RequestParam Integer gradeId)
    {
        InputStream in = null;
        try {
            in = new FileInputStream("D://lessonTemplate.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CheckResult<String> cr = new CheckResult<>();
        try {
            List<List<String>> data = POIUtil.getBankListByExcel(in, "lessonTemplate.xlsx", 9, 2);
            List<TaskInfo> taskInfos = new ArrayList<>();
            for(List l:data)
            {
                TaskInfoCustom taskInfo = parseTaskInfo(l);
                taskInfo.setType(type);
                taskInfo.setGrade(new Grade(gradeId));
                taskInfos.add(taskInfo);
            }
            lessonService.addLesson(taskInfos,type,gradeId);
            cr.setData("导入成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError(e.getMessage());
        }
        return cr;
    }

    /**
     * 解析任务
     * @param list
     * @return
     */
    private TaskInfoCustom parseTaskInfo(List<String> list)
    {
        TaskInfoCustom taskInfo = new TaskInfoCustom();
        taskInfo.setSign(Integer.valueOf(list.get(0)));
        if(!list.get(1).isEmpty())
            taskInfo.setNum(Integer.valueOf(list.get(1)));
        taskInfo.setName(list.get(2));
        //装填周次
        List<TaskCompletion> completions = new ArrayList<>();
        TaskCompletion completion;
        if(list.get(3).contains("周"))
            list.set(3,list.get(3).replaceAll("周",""));
        //解析周次
        if(list.get(3).contains("-"))
        {
            String[] week = list.get(3).split("-");
            for(int i = Integer.parseInt(week[0]);i<Integer.parseInt(week[1]);i++)
            {
                completion = new TaskCompletion();
                completion.setWeek(i);
                completions.add(completion);
            }
        }else if(list.get(3).contains(" "))
        {
            String[] weeks = list.get(3).split(",");
            for(int i = 0 ;i<weeks.length;i++)
            {
                completion = new TaskCompletion();
                completion.setWeek(Integer.parseInt(weeks[i]));
                completions.add(completion);
            }
        }
        taskInfo.setCompletions(completions);
        taskInfo.setWeekday(Integer.valueOf(list.get(4)));
        taskInfo.setStart(new Times(Integer.valueOf(list.get(5))));
        taskInfo.setEnd(new Times(Integer.valueOf(list.get(6))));
        if(!list.get(7).isEmpty())
            taskInfo.setRoom(new ClassRoom(Integer.valueOf(list.get(7))));
        if(!list.get(8).isEmpty())
            taskInfo.setSponsor(new User(list.get(8), UserType.TEACHER));

        return taskInfo;
    }

    /**
     * 更新课程编号
     * @param id
     * @param num
     * @return
     */
    @RequestMapping(value = "/updateLessonNum",
    produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public CheckResult<String> updateLessonNum(@RequestParam Integer id,@RequestParam String num)
    {
        CheckResult<String> cr = new CheckResult<>();
        try {
            lessonService.updateLessonNum(id,num);lessonService.updateLessonNum(id,num);
            cr.setData("更新成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("未知错误");
        }
        return cr;
    }
}
