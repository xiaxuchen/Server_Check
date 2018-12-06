package com.cxyz.check.handler;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.entity.ClassRoom;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Times;
import com.cxyz.check.exception.task.NoTaskException;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.util.excel.POIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Completion;

@Controller
@RequestMapping(value = "/task")
public class TaskHandler {


    @Autowired
    private TaskService taskService;

    /**
     * 导入课表
     * @param file excel文件
     * @param termId 学期id
     * @param type 课程类型
     * @return
     */
    @RequestMapping(value = "/addTask",
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public CheckResult<String> addTask(@RequestParam MultipartFile file,
                                       @RequestParam int termId,@RequestParam int type,@RequestParam Integer gradeId)
    {
        CheckResult<String> checkResult = new CheckResult<>();
        try {
            List<List<String>> list = POIUtil.getBankListByExcel(file.getInputStream(), file.getOriginalFilename());
            List<TaskInfo> taskInfos = new ArrayList<>();
            list.remove(0);
            for(List<String> strings:list)
            {
                taskInfos.add(parseTask(strings));
            }
            taskService.addTask(taskInfos,termId,type,gradeId);
            checkResult.setData("导入成功!");
            checkResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            checkResult.setError("导入失败!");
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

}
