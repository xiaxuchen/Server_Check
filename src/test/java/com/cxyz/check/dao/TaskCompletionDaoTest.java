package com.cxyz.check.dao;

import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.date.DateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class TaskCompletionDaoTest {

    @Resource
    private TaskCompletionDao completionDao;
    @Test
    public void getCompsByTID() {
    }

    @Test
    public void findById()
    {
        System.out.println(completionDao.findById(1));
        /**
         * 测试结果：成功
         * TaskCompletion [_id=null, taskInfo=TaskInfo{id='null', name='java', sponsor=OldUser{id='17478093', name='null', sex='null', pwd='null', phone='null', photo='null', power=null, type=0}, checker=OldUser{id='17478093', name='null', sex='null', pwd='null', phone='null', photo='null', power=null, type=0}, start=1970-01-01 00:00:00.0, end=1970-01-01 23:40:00.0, room=ClassRoom{id=21, name='null', college=null, state=null}, type=0, grade=Grade [name=null, college=null, headTeacher=null, classRoom=null, id=null], completions=[]}, date=Wed Oct 17 00:00:00 CST 2018, state=1, updatetime=0118-10-08 16:56:57.0]
         **/
    }

    @Test
    public void getTaskCompById() {
        DateTime time = DateTime.fromUDate(new java.util.Date());
        System.out.println(time.getTime());
        System.out.println(completionDao.selectList(122,time.
                getTime(),time.getDate(),"17478093", UserType
                .STUDENT, 0));
        /**
         * 测试结果：成功
         * [TaskCompletion [_id=1, taskInfo=TaskInfo{id='null', name='java', sponsor=OldUser{id='17478093', name='null', sex='null', pwd='null', phone='null', photo='null', power=null, type=0}, checker=OldUser{id='null', name='null', sex='null', pwd='null', phone='null', photo='null', power=null, type=null}, start=1970-01-01 00:00:00.0, end=1970-01-01 23:40:00.0, room=ClassRoom{id=21, name='null', college=null, state=null}, type=null, grade=Grade [name=null, college=null, headTeacher=null, classRoom=null, id=null], completions=[]}, date=Wed Oct 17 00:00:00 CST 2018, state=1, updatetime=0118-10-08 16:56:57.0]]
         * 注意不要乱设断点，不然就一直给你报错没有连接
         **/
    }

    @Test
    public void addComp() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setCompletions(new ArrayList<TaskCompletion>(){{
            TaskCompletion taskCompletion = new TaskCompletion();
            taskCompletion.setDate(new Date());
            TaskCompletion taskCompletion1 = new TaskCompletion();
            taskCompletion1.setDate(new Date());
            add(taskCompletion);
            add(taskCompletion1);
        }});
        ArrayList<TaskInfo> list = new ArrayList<>();
        list.add(taskInfo);
        list.add(taskInfo);
        System.out.println(list);
        completionDao.addComp(list);
    }

    @Test
    public void updateCompState() {
        System.out.println(completionDao.updateCompState(12,2));
        /**
         * 测试结果:成功
         * 1
         */
    }

    @Test
    public void getCompSponsorId() {
        completionDao.getCompSponsorId(227);
    }

    @Test
    public void findById1() {
        System.out.println(completionDao.findByTaskId(42,1));
    }
}