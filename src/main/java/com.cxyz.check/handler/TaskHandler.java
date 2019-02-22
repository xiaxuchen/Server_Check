package com.cxyz.check.handler;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.GradeLessonDto;
import com.cxyz.check.dto.SubjectDto;
import com.cxyz.check.entity.ClassRoom;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Times;
import com.cxyz.check.exception.envir.LessonImportedException;
import com.cxyz.check.exception.task.NoTaskException;
import com.cxyz.check.service.EnvirService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.util.excel.POIUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/task")
public class TaskHandler {


    @Autowired
    private TaskService taskService;

    @Autowired
    private EnvirService envirService;

    /**
     * 导入课表
     * @param file excel文件
     * @param type 课程类型
     * @return
     */
    @RequestMapping(value = "/addTask",
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public CheckResult<String> addTask(@RequestParam MultipartFile file,
                                       @RequestParam Integer type,@RequestParam Integer gradeId)
    {
        CheckResult<String> checkResult = new CheckResult<>();
        try {
            if(envirService.isLessonImportEnable(gradeId))//如果可导入则导入
            {
                try {
                    List<List<String>> list = POIUtil.getBankListByExcel(file.getInputStream(), file.getOriginalFilename());
                    List<TaskInfo> taskInfos = new ArrayList<>();
                    list.remove(0);
                    for(List<String> strings:list)
                    {
                        //判断是否为空list，若空则continue
                        if(strings.isEmpty()||strings.size()<6)
                            continue;

                        boolean isEmpty[] = {true};
                        strings.forEach(s -> {
                            if(!s.isEmpty())
                                isEmpty[0] = false;
                        });
                        if(isEmpty[0])
                            continue;

                        TaskInfo taskInfo = parseTask(strings);
                        if(taskInfo == null)
                            continue;
                        taskInfos.add(taskInfo);
                    }
                    taskService.addTask(taskInfos,type,gradeId);
                    checkResult.setData("导入成功!");
                    checkResult.setSuccess(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    checkResult.setError("导入失败!");
                }
            }else {
                checkResult.setError("课程导入暂未开启");
            }
        }catch (LessonImportedException e)
        {
            checkResult.setError(e.getMessage());
        }catch (Exception e)
        {
            checkResult.setError("服务器内部异常");
        }
        return checkResult;
    }


    /**
     * 解析字符串为taskinfo类型
     * @param info
     * @return
     */
    private TaskInfo parseTask(List<String> info)
    {

        if(info.size()<6)
            return null;
        TaskInfo taskInfo = new TaskInfo();
        //装填名字
        taskInfo.setName(info.get(0));
        //装填周次
        List<TaskCompletion> completions = new ArrayList<>();
        TaskCompletion completion;
        if(info.get(1).contains("周"))
            info.set(1,info.get(1).replaceAll("周",""));
        //解析周次
        if(info.get(1).contains("-"))
        {
            String[] week = info.get(1).split("-");
            for(int i = Integer.parseInt(week[0]);i<Integer.parseInt(week[1]);i++)
            {
                completion = new TaskCompletion();
                completion.setWeek(i);
                completions.add(completion);
            }
        }else if(info.get(1).contains(","))
        {
            String[] weeks = info.get(1).split(",");
            for(int i = 0 ;i<weeks.length;i++)
            {
                completion = new TaskCompletion();
                completion.setWeek(Integer.parseInt(weeks[i]));
                completions.add(completion);
            }
        }
        taskInfo.setCompletions(completions);
        //填充星期
        taskInfo.setWeekday(Integer.parseInt(info.get(2)));
        //填充开始节次和结束节次
        taskInfo.setStart(new Times(Integer.parseInt(info.get(3))));
        taskInfo.setEnd(new Times(Integer.parseInt(info.get(4))));
        ClassRoom classRoom = new ClassRoom();
        classRoom.setName(info.get(5));
        taskInfo.setRoom(classRoom);
        return taskInfo;
    }


    /**
     * 检查是否有考勤任务
     * @param checkerId 考勤人id
     * @param checkerType 考勤人类型
     * @param type 考勤类型·
     * @return
     */
    @RequestMapping(value = "/checkTask",
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<CheckTaskDto>
    checkTask(@RequestParam String checkerId, @RequestParam int checkerType
            , @RequestParam int type)
    {
        try {
            CheckTaskDto taskDto = taskService.checkTask(checkerId, checkerType, type);
            return new CheckResult<CheckTaskDto>(taskDto);
        }catch (NoTaskException e)
        {
            return new CheckResult("当前暂无考勤任务");
        }
    }

    @RequestMapping(value = "/getSubjects",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<SubjectDto>> getSubjects(@RequestParam Integer gradeId)
    {
        try {
            return new CheckResult<>(taskService.getSubjects(gradeId));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new CheckResult<>("服务器异常");
        }

    }

    /**
     * 获取考勤统计表
     * @param response
     * @param sponsorId 用户id
     * @param sponsorType 用户类型
     * @param lessonId 课程id
     * @throws IOException
     */
    @RequestMapping(value = "/getStatisticExcel",
            method = {RequestMethod.GET}
    )
    public void getStatisticExcel(HttpServletResponse response,
                                  @RequestParam String sponsorId,@RequestParam Integer sponsorType,
                                  @RequestParam Integer lessonId) throws IOException {
        try {
            Workbook workbook = taskService.getStatisticExcel(sponsorId,sponsorType,lessonId);
            response.setHeader("content-disposition", "attachment;filename=statisic.xlsx");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            response.setContentType("text/html;charset=utf-8");
            e.printStackTrace(response.getWriter());
        }catch (Exception e)
        {
            response.setContentType("text/html;charset=utf-8");
            e.printStackTrace(response.getWriter());
        }
    }


    @RequestMapping(value = "/getGradeTasks",
    method = RequestMethod.GET,
    produces = "application/json;charset=UTF-8;")
    @ResponseBody
    public CheckResult<List<GradeLessonDto>> getGradeTasks(@RequestParam String sponsorId, @RequestParam Integer sponsorType, @RequestParam(required = false)Integer termId)
    {
        CheckResult<List<GradeLessonDto>> cr = new CheckResult<>();
        try {
            cr.setData(taskService.getGradeTasks(sponsorId,sponsorType,termId));
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器异常");
        }
        return cr;
    }
}
