package com.cxyz.check.service.impl;

import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.exception.record.NOHistoryException;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.automapper.AutoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    //一次加载考勤记录的条数
    private static final int LEN = 10;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private TaskDao taskDao;


    @Override
    public CheckRecordDto getCheckRecord(String id, int type, int grade) {
        List<CheckRecord> checkRecords = recordDao.getCheckRecords(id, 0, LEN);
        List<CheckRecordDto.RecordInfo> recordInfos = AutoMapper.mappingList(checkRecords, CheckRecordDto.RecordInfo.class);
        CheckRecordDto checkRecordDto = new CheckRecordDto();
        checkRecordDto.setRecordInfos(recordInfos);
        checkRecordDto.setAll(taskDao.getGradeCheck(grade));
        return checkRecordDto;
    }

    @Override
    public List<CheckHistoryDto> getHistory(String id, int type) {
        return loadMore(id,type,0);
    }

    @Override
    public List<CheckHistoryDto> loadMore(String id, int type, int start) throws NOHistoryException {
        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("用户名有误!");
        List<CheckHistoryDto> history = recordDao.getHistory(id, type,start,LEN);
        if(history.size() == 0)
            throw new NOHistoryException("暂无考勤历史");
        return history;
    }
}
