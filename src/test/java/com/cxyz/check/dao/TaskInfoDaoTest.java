package com.cxyz.check.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
public class TaskInfoDaoTest {

    @Resource
    private TaskInfoDao taskInfoDao;
    @Test
    public void getTaskInfos() {
        System.out.print(taskInfoDao.getTaskInfos(122));
    }

    @Test
    public void addTasks() {
    }
}