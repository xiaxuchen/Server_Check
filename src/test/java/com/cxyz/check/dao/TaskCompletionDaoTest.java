package com.cxyz.check.dao;

import com.cxyz.check.util.date.DateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
public class TaskCompletionDaoTest {

    @Resource
    private TaskCompletionDao completionDao;
    @Test
    public void getCompsByTID() {
    }

    @Test
    public void getTaskCompById() {
        DateTime time = DateTime.fromUDate(new java.util.Date());
        System.out.println(time.getTime());
        System.out.println(completionDao.selectOne(122,time.getTime(),time.getDate()));
        /**
         * 测试结果：成功
         * [TaskCompletion [_id=1, taskInfo=null, date=Tue Oct 16 00:00:00 CST 2018, state=1, updatetime=0118-10-08 16:56:57.0], TaskCompletion [_id=2, taskInfo=null, date=Tue Oct 16 00:00:00 CST 2018, state=12, updatetime=null]]
         * 注意不要乱设断点，不然就一直给你报错没有连接
         **/
    }

    @Test
    public void addComp() {
    }

    @Test
    public void updateCompState() {
        System.out.println(completionDao.updateCompState(12,2));
        /**
         * 测试结果:成功
         * 1
         */
    }
}