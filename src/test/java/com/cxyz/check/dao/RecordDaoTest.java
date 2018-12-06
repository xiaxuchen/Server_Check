package com.cxyz.check.dao;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.typevalue.CheckRecordResult;

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
        System.out.println(dao.getCheckRecords("17478093",0,10));
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
        System.out.println(dao.getHistory("17478093",0));
    }
}