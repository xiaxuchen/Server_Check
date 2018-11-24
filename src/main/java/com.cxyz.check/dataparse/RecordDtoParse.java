package com.cxyz.check.dataparse;

import com.cxyz.check.dto.normal.RecordDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.Student;
import com.cxyz.check.entity.TaskCompletion;

public class RecordDtoParse {

    public static final CheckRecord getRecord(RecordDto dto,int compid)
    {
        CheckRecord record = new CheckRecord();
        record.setDes(dto.getDes());
        record.setStu(new Student(dto.getId()));
        record.setResult(dto.getResult());
        record.setCompletion(new TaskCompletion(compid));
        return record;
    }
}
