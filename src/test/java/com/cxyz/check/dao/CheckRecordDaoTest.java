package com.cxyz.check.dao;

import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.Student;
import com.cxyz.check.entity.TaskCompletion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
public class CheckRecordDaoTest {

    @Resource
    private CheckRecordDao checkRecordDao;
    @Test
    public void getRecordByStuId() {

        System.out.println(checkRecordDao.getRecordByStuId
                ("17478093",0,10));
        /**
         * 测试结果：成功
         * [CheckRecord{id=25, stu=null, result=4, comp=TaskCompletion [_id=1, taskInfo=null, date=null, state=null, updatetime=null], des='null'}]
         */
    }

    @Test
    public void getSingleRecordById() {
    }

    @Test
    public void addRecords() {
        ArrayList<CheckRecord> r = new ArrayList<CheckRecord>();
        CheckRecord c = new CheckRecord();
        c.setComp(new TaskCompletion(1));
        c.setDes("cainima");
        c.setStu(new Student("17478280"));
        c.setResult(1);
        r.add(c);
        r.add(c);
        checkRecordDao.addRecords(r);
    }
}