package com.cxyz.check.dao;
import com.cxyz.check.dto.AlterRecordDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.CheckRecordResult;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RecordDaoTest {

    @Resource
    private RecordDao dao;
    @Test
    public void getCheckRecords() {
        System.out.println(dao.getRecordStatistic("17478093"));
    }

    @Test
    public void getHistory()
    {
        System.out.println(dao.getHistory("17478093",0,0,10));
    }

    @Test
    public void getSingleRecordById() {
    }

    @Test
    public void addRecords() {
        /**
         * 测试成功
         */
        CommitCheckDto.StuInfo info = new CommitCheckDto.StuInfo();
        info.setId("17478069");
        info.setDes("这个人是狗");
        info.setResult(CheckRecordResult.ABSENTEEISM);
        List<CommitCheckDto.StuInfo> stuInfos = new ArrayList<CommitCheckDto.StuInfo>();
        stuInfos.add(info);
        dao.addRecords(1,stuInfos);
    }

    @Test
    public void addOtherState() {
        /**
         * 测试成功
         */
        //dao.addOtherState(1,"狗东西打了老师，老师罢课了");
        System.out.println(dao.getHistory("17478093",0,0,1));
    }

    @Test
    public void getAlterRecords()
    {
        System.out.println(dao.getAlterRecords(1702,163));
    }

    @Test
    public void updateRecord() {
        AlterRecordDto dto = new AlterRecordDto();
        dto.setDes("caoniamdasdas");
        dto.setResult(10);
        dto.setStu(new GradeStusDto("17478093"));
        dao.updateRecord(163,dto);
    }

    @Test
    public void getAlterRecord() {
        dao.getAlterRecord("17478063",163);
    }

    @Test
    public void removeRecords() {
        ArrayList arrayList = new ArrayList() {{
            CheckRecord record1 = new CheckRecord();
            record1.setStu(new User("17478056"));
            add(record1);
        }};
        dao.removeRecords(163,arrayList);
    }

    @Test
    public void getStatisticRecords() {
        System.out.println(dao.getStatisticRecords("2018-9-3","2018-9-4",1702,0));
    }

    @Test
    public void getRecordsByCompId() {
        System.out.println(dao.getRecordsByCompId(237));
    }

    @Test
    public void getMyHistory() {
        Logger logger = Logger.getLogger(RecordDao.class);
        logger.debug(dao.getMyHistory("17478093",null,0,10));
    }

    @Test
    public void getLessonHistories()
    {
        System.out.println(dao.getLessonHistories(493,0,10));
    }
}