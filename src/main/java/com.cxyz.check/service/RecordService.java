package com.cxyz.check.service;

import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.exception.record.NOHistoryException;
import com.cxyz.check.exception.task.NoTaskException;

import java.util.List;

public interface RecordService {

    /**
     * 获取最近10条考勤违规记录
     * @param id 用户id
     * @param type 用户类型
     * @param grade 用户班级id
     * @return
     */
    CheckRecordDto getCheckRecord(String id, int type, int grade);

    /***
     * 获取历史考勤信息
     * @param id 考勤人id
     * @param type 考勤人类型
     * @return
     */
    List<CheckHistoryDto> getHistory(String id , int type) throws NOHistoryException;

    /**
     * 加载更多历史考勤
     * @param id 考勤人id
     * @param type 考勤人类型
     * @param start 开始条目
     * @return
     * @throws NOHistoryException
     */
    List<CheckHistoryDto> loadMore(String id , int type ,int start) throws  NOHistoryException;

}
