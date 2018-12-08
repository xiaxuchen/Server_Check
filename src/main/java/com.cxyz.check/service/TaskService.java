package com.cxyz.check.service;

import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.exception.task.NoTaskException;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理考勤任务的service
 */
public interface TaskService {

    /**
     * 检查当前是否有考勤任务
     * @param checker_id 考勤人id
     * @param checker_type 考勤人类型
     * @param type 任务类型
     * @return
     */
    CheckTaskDto checkTask(String checker_id, int checker_type,
                           int type) throws NoTaskException;

    /**
     * 提交考勤记录
     * @param checkDto 考勤记录的dto
     * @return
     */
    void commitCheck(CommitCheckDto checkDto);

    /**
     * 通过班级id获取班级所以学生名单
      * @param grade 班级id
     * @return
     */
    List<GradeStusDto> gradeStus(int grade);

    /**
     * 添加考勤任务
     * @param taskInfos 考勤任务
     */
    void addTask(List<TaskInfo> taskInfos,Integer type,Integer gradeId);

}
