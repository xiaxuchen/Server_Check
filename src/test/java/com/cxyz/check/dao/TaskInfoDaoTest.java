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
    public void getTasks()
    {
        System.out.print(taskInfoDao.getTasks(122));
        /**测试结果：成功
         * [TaskInfo{id='AA2CCA2895974AC8B04C8E63F2364886', name='java', sponsor=OldUser{id='17478093', name='null', sex='null', pwd='null', phone='null', photo='null', power=null, type=0}, checker=OldUser{id='17478093', name='null', sex='null', pwd='null', phone='null', photo='null', power=null, type=0}, start=null, len=null, end=null, room=ClassRoom{id=21, name='null', college=null, state=null}, type=0, grade=Grade [name=null, college=null, headTeacher=null, classRoom=null, id=122], completions=[TaskCompletion [_id=1, taskInfo=null, date=null, state=1, updatetime=null], TaskCompletion [_id=2, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=3, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=4, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=5, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=6, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=7, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=8, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=9, taskInfo=null, date=null, state=0, updatetime=null], TaskCompletion [_id=10, taskInfo=null, date=null, state=0, updatetime=null]]}]Disconnected from the target VM, address: '127.0.0.1:49340', transport: 'socket'
         */
    }

    @Test
    public void addTasks() {
    }
}