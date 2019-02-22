package com.cxyz.check.dao;

import com.cxyz.check.entity.Grade;
import com.cxyz.check.entity.Lesson;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Term;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.LessonType;
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
public class LessonDaoTest {

    @Autowired
    private LessonDao dao;

    @Autowired
    private SchoolDao schoolDao;
    @Test
    public void addLessons() {
        TaskInfo l = new TaskInfo();
        l.setName("离散数学");
        l.setTerm(new Term(19));
        l.setGrade(new Grade(1702));
        l.setType(LessonType.NORMAL);
        l.setSponsor(new User("17478093", UserType.TEACHER));
        ArrayList<TaskInfo> list = new ArrayList<>();
        list.add(l);
    }

    @Test
    public void getLesson() {
        System.out.println(dao.getLesson("17478093",UserType.TEACHER,443));
    }

    @Test
    public void updateLessonNum()
    {
        dao.updateLessonNum(493,"564146");
    }
}