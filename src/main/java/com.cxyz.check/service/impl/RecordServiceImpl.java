package com.cxyz.check.service.impl;

import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.util.automapper.AutoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private TaskDao taskDao;

    //getCheckRecord方法获取的记录条数
    private static final int LEN = 10;


    @Override
    public CheckRecordDto getCheckRecord(String id, int type, int grade) {
        List<CheckRecord> checkRecords = recordDao.getCheckRecords(id, 0, LEN);
        List<CheckRecordDto.RecordInfo> recordInfos = AutoMapper.mappingList(checkRecords, CheckRecordDto.RecordInfo.class);
        CheckRecordDto checkRecordDto = new CheckRecordDto();
        checkRecordDto.setRecordInfos(recordInfos);
        checkRecordDto.setAll(taskDao.getGradeCheck(grade));
        return checkRecordDto;
    }
}
