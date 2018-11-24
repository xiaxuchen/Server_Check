package com.cxyz.check.dao;

import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.exception.task.NoTaskException;

import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Type;
import java.util.List;

public interface TaskCompletionDao {


    /**
     * 通过id查询一个完成情况
     * @param id
     * @return
     */
    TaskCompletion findById(@Param("id")int id);

    /**
     * 通过班级id和当前时间获取考勤任务
     * @param grade 班级id
     * @param time 当前时间
     * @param date 当前日期
     * @param checker 考勤人id
     * @param c_type 考勤人类型
     * @param type 考勤类型
     * @return
     */
    List<TaskCompletion> selectList(@Param("grade") int grade,
                                    @Param("time") String time,
                                    @Param("date")String date,
                                    @Param("checker") String checker,
                                    @Param("c_type") int c_type,
                                    @Param("type")int type);


    /**
     * 更新考勤情况的状态
     * @param state 状态
     * @param id 考勤完成情况id
     * @return
     */
    int updateCompState(@Param("state") int state,@Param("id") int id);

    /**
     * 通过一下参数获取当前是否有考勤任务
     * @param grade 班级id
     * @param checker_id 考勤人id
     * @param checker_type 考勤人类型
     * @param date 考勤日期
     * @param time 当前时间
     * @param type 考勤的类型
     * @return 考勤任务Dto
     */
    CheckTaskDto checkTask(@Param("grade")int grade,
                           @Param("checker_id")int checker_id, @Param("checker_type")int checker_type,
                           @Param("date")String date,@Param("type") int type,
                           @Param("time") String time) throws NoTaskException;
}
