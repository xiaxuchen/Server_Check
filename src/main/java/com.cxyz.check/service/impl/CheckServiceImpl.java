package com.cxyz.check.service.impl;

import com.cxyz.check.dao.CheckRecordDao;
import com.cxyz.check.dao.StudentDao;
import com.cxyz.check.dao.TaskCompletionDao;
import com.cxyz.check.dao.TaskInfoDao;
import com.cxyz.check.dao.TeacherDao;
import com.cxyz.check.dataparse.RecordDtoParse;
import com.cxyz.check.dto.CheckCommitDto;
import com.cxyz.check.dto.normal.RecordDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.exception.CommitFailException;
import com.cxyz.check.service.CheckService;
import com.cxyz.check.typevalue.TaskCompletionState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private TaskInfoDao taskInfoDao;
    @Autowired
    private TaskCompletionDao taskCompletionDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;


    @Override
    public List<CheckRecord> getMineRecords(String id, int start) {
        return null;
    }

    @Override
    @Transactional
    public boolean CommitRecord(CheckCommitDto commitDto) {
        if(commitDto.getType() == TaskCompletionState.COMPLE)
        {
            List<RecordDto> recordDtos = commitDto.getRecordDtos();
            List<CheckRecord> checkRecords = new ArrayList<CheckRecord>();
            CheckRecord record = null;
            for(RecordDto dto:recordDtos)
            {
                record = RecordDtoParse.getRecord(dto,commitDto.getCompId());
                checkRecords.add(record);
            }
            int adds = checkRecordDao.addRecords(checkRecords);
            int updates = taskCompletionDao.updateCompState(commitDto.getType(), commitDto.getCompId());
            if(updates!=1||adds!=commitDto.getRecordDtos().size())
                throw new CommitFailException();
            return true;
        }
        return false;
    }
}
