package com.cxyz.check.service;

import com.cxyz.check.dto.AlterRecordDto;
import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.StatisticDto;
import com.cxyz.check.dto.StatisticRecordDto;
import com.cxyz.check.exception.record.NOHistoryException;
import com.cxyz.check.exception.record.NoMoreHistoryException;
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
    List<CheckHistoryDto> loadMore(String id , int type ,int start) throws NoMoreHistoryException;

    /**
     * TODO 当获取为空时抛异常
     * 获取历史考勤详细信息
     * @param gradeId 班级id
     * @param compId 考勤完成情况id
     * @return
     */
    List<AlterRecordDto> getAlterRecords(Integer gradeId,Integer compId);

    /**
     * 更新修改的记录
     * @param alterRecordDtos
     * @param updaterId 更新人id
     * @param updaterType 更新人类型
     */
    void updateRecords(Integer compId,List<AlterRecordDto> alterRecordDtos,String updaterId,Integer updaterType);

    /**
     * 获取班级统计结果
     * @param start 开始时间
     * @param end 结束时间
     * @param gradeId 班级id
     * @return
     */
    StatisticDto getStatistic(String start,String end,Integer gradeId);

    /**
     * 统计页面获取记录详情
     * @param start 开始时间
     * @param end 结束时间
     * @param gradeId 班级id
     * @param resultType 结果
     * @return
     */
    List<StatisticRecordDto> getStatisticRecords(String start,String end,Integer gradeId,Integer resultType);
}
