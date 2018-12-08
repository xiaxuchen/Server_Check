package com.cxyz.check.handler;


import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.exception.record.NOHistoryException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.util.parse.GsonUtil;

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
    private RecordDao dao;

    /**
     * 获取考勤记录
     * @param id 用户id
     * @param type 用户类型
     * @param grade 用户班级id
     * @return
     */
    @RequestMapping(value = "/getRecords",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CheckResult<CheckRecordDto>
    getRecords(@RequestParam String id,
               @RequestParam int type,@RequestParam int grade)
    {
        return new CheckResult<>(recordService
                .getCheckRecord(id,type,grade));
    }


    @Autowired
    private TaskService taskService;

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
            checkResult = new CheckResult<>("暂无考勤历史");
        }
        return checkResult;
    }


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
        } catch (NOHistoryException e)
        {
            checkResult = new CheckResult<>("暂无考勤历史");
        }
        return checkResult;
    }
}
