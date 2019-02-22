package com.cxyz.check.dao;

import com.cxyz.check.entity.ClassRoom;
import com.cxyz.check.entity.Grade;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Times;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.UserType;

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
public class TaskDaoTest {

    @Resource
    private TaskDao dao;
    @Test
    public void checkTask() {
        System.out.println(dao.checkTask("17478093",
                UserType.STUDENT,"2018-09-03",1,"08:00:00"));
    }

    @Test
    public void getCompTime()
    {
        System.out.println(dao.getCompDate(1));
    }

    @Test
    public void addTask()
    {
        List<TaskInfo> list = new ArrayList<>();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setName("离散");
        User u = new User("17478093");
        u.setType(UserType.STUDENT);
        taskInfo.setChecker(u);
        taskInfo.setGrade(new Grade(1702));
        taskInfo.setWeekday(1);
        taskInfo.setWeekday(1);
    }

    @Test
    public void getTaskInfoById()
    {
    }

    @Test
    public void getGradeTask()
    {
        System.out.println(dao.getGradeTasks("16478040",UserType.TEACHER,19));
    }


    @Test
    public void getSubject()
    {
        System.out.println(dao.getSubjects(1702,19));
    }


}