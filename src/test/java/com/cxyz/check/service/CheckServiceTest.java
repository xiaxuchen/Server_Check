package com.cxyz.check.service;

import com.cxyz.check.dto.CheckCommitDto;
import com.cxyz.check.dto.normal.RecordDto;
import com.cxyz.check.entity.typevalue.ResultType;
import com.cxyz.check.entity.typevalue.TaskState;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring_dao.xml",
                "classpath:spring/spring-service.xml"}
)
public class CheckServiceTest {

    @Autowired
    private CheckService checkService;
    @Test
    public void getMineRecords() {
        CheckCommitDto checkCommitDto = new CheckCommitDto();
        checkCommitDto.setCompId(1);
        checkCommitDto.setType(TaskState.COMPLE);
        RecordDto recordDto = new RecordDto();
        recordDto.setDes("qusiba");
        recordDto.setId("17478888");
        recordDto.setResult(ResultType.LATE);
        List<RecordDto> recordDtos = new ArrayList<RecordDto>();
        recordDtos.add(recordDto);
        checkCommitDto.setRecordDtos(recordDtos);
        System.out.println(checkService.CommitRecord(checkCommitDto));
        //System.out.println(checkService.getMineRecords("17478093",0));
    }
}