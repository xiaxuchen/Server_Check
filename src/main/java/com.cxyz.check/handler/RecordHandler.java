package com.cxyz.check.handler;


import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dto.AlterRecordDto;
import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.MyHistoryDto;
import com.cxyz.check.dto.StatisticDto;
import com.cxyz.check.dto.StatisticRecordDto;
import com.cxyz.check.exception.record.NOHistoryException;
import com.cxyz.check.exception.record.NoMoreHistoryException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.util.parse.GsonUtil;
import com.google.gson.reflect.TypeToken;

import org.apache.poi.hssf.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/record")
public class RecordHandler {

    @Autowired
    private RecordService recordService;


    @Autowired
    private TaskService taskService;

    /**
     * 获取考勤记录
     * @param id 用户id
     * @param type 用户类型
     * @param grade 用户班级id
     * @return
     */
    @RequestMapping(value = "/getRecordStatistic",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<CheckRecordDto>
    getRecordStatistic(@RequestParam String id,
               @RequestParam int type,@RequestParam int grade)
    {
        CheckResult<CheckRecordDto> cr = new CheckResult<>();
        try {
            cr.setData(recordService.getRecordStatistic(id,type,grade));
        }catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }


    /**
     * 提交考勤信息
     * @param commitCheck 考勤结果
     * @return
     */
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


    /**
     * 获取考勤历史记录
     * @param id 考勤人id
     * @param type 考勤人类型
     * @return
     */
    @RequestMapping(value = "/getHistory",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<CheckHistoryDto>>
    getHistory(@RequestParam String id,
               @RequestParam int type) {
        CheckResult<List<CheckHistoryDto>> checkResult;
        try {
             checkResult = new CheckResult<>(recordService.getHistory(id,type));
        } catch (NOHistoryException e)
        {
            checkResult = new CheckResult<>(e.getMessage());
        }
        return checkResult;
    }


    /**
     * 加载更多考勤历史记录
     * @param id 考勤人id
     * @param type 考勤人类型
     * @return
     */
    @RequestMapping(value = "/loadMore",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<CheckHistoryDto>>
    loadMore(@RequestParam String id,
               @RequestParam int type,@RequestParam int start) {
        CheckResult<List<CheckHistoryDto>> checkResult;
        try {
            checkResult = new CheckResult<>(recordService.loadMore(id,type,start));
        } catch (NoMoreHistoryException e)
        {
            checkResult = new CheckResult<>(e.getMessage());
        }
        return checkResult;
    }

    /**
     * 获取历史记录详细
     * @param gradeId
     * @param compId
     * @return
     */
    @RequestMapping(value = "/getAlterRecords",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<List<AlterRecordDto>>
    getAlterRecords(@RequestParam Integer gradeId,
             @RequestParam Integer compId) {
        return new CheckResult<>(recordService.getAlterRecords(gradeId,compId));
    }

    /**
     * 更新考勤记录
     * @param compId 考勤完成情况id
     * @param alteds 修改记录
     * @param updaterId 更新人id
     * @param updaterType 更新人类型
     * @return
     */
    @RequestMapping(value = "/updateAlteds",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<String>
    updateAlteds(@RequestParam Integer compId,
                    @RequestParam String alteds,@RequestParam String updaterId,@RequestParam Integer updaterType) {
        CheckResult<String> checkResult = new CheckResult<>();
        try {
            List<AlterRecordDto> dtos = (List<AlterRecordDto>) GsonUtil.fromJson(alteds, new TypeToken<List<AlterRecordDto>>(){}.getType());
            recordService.updateRecords(compId,dtos,updaterId,updaterType);
            checkResult.setSuccess(true);
            checkResult.setData("提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            checkResult.setSuccess(false);
            checkResult.setData("服务器异常");
        }
        return checkResult;
    }

    /**
     * url:/record/getStatistic
     * 获取统计数
     * @param start 开始日期
     * @param end 结束日期
     * @param gradeId 班级id
     * @return
     */
    @RequestMapping(value = "getStatistic",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"})
    @ResponseBody
    public CheckResult<StatisticDto> getStatistic(@RequestParam String start,
                                                  @RequestParam String end,@RequestParam Integer gradeId)
    {
        try {
            return new CheckResult<>(recordService.getStatistic(start,end,gradeId));
        }catch (Exception e)
        {
            return new CheckResult<>("服务器异常");
        }
    }


    /**
     * url:/record/getStatisticRecords
     * 获取记录
     * @param start 开始日期
     * @param end 结束日期
     * @param gradeId 班级id
     * @param resultType 考勤结果类型
     * @return
     */
    @RequestMapping(value = "getStatisticRecords",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8;"})
    @ResponseBody
    public CheckResult<List<StatisticRecordDto>> getStatisticRecords(@RequestParam String start,
               @RequestParam String end,@RequestParam Integer gradeId,@RequestParam Integer resultType)
    {
        try {
            return new CheckResult<>(recordService.getStatisticRecords(start,end,gradeId,resultType));
        }catch (Exception e)
        {
            return new CheckResult<>("服务器异常");
        }
    }

    /**
     * 获取个人考勤信息
     * @param id
     * @param result
     * @param start
     * @return
     */
    @RequestMapping(value = "/getMyHistory",
        method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8;"
    )
    @ResponseBody
    public CheckResult<List<MyHistoryDto>> getMyHistory
            (@RequestParam String id,@RequestParam Integer result,@RequestParam Integer start) {
        CheckResult<List<MyHistoryDto>> cr = new CheckResult<>();
        try {
            cr.setData(recordService.getMyHistory(id, result, start));
        } catch (NoMoreHistoryException e)
        {
            cr.setError("没有更多数据了");
        }
        catch (Exception e)
        {
            cr.setError("服务器异常");
        }
        return cr;
    }


    @RequestMapping(value = "/getLessonHistories",
            method = RequestMethod.GET,
            produces = {"application/json;charset=utf-8"}
    )
    @ResponseBody
    /**
     * 获取课程的历史记录
     * @param id 课程id
     * @param start 起始位置
     */
    public CheckResult<List<CheckHistoryDto>> getLessonHistories(@RequestParam Integer id,@RequestParam(required = false) Integer start)
    {
        if(start == null)
            start = 0;
        CheckResult<List<CheckHistoryDto>> cr = new CheckResult<>();
        try {
            List<CheckHistoryDto> lessonHistories = recordService.getLessonHistories(id, start);
            cr.setData(lessonHistories);
        }catch (NoMoreHistoryException e)
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