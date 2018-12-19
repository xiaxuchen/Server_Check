package com.cxyz.check.dao;

import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.Update;
import com.cxyz.check.typevalue.UserType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UpdateDaoTest {
    @Autowired
    UpdateDao dao;

    @Autowired
    RecordDao recordDao;
    @Test
    public void addUpdates() {
        dao.addUpdates(new ArrayList(){
            {
                CheckRecord r = recordDao.getAlterRecord("17478063", 163);
                r.setCompletion(new TaskCompletion(163));
                add(r);
                add(r);
            }
        },"17478093", UserType.STUDENT);
    }
}