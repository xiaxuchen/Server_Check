package com.cxyz.check.dao;

import com.cxyz.check.entity.School;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class GradeDaoTest {

    @Autowired
    private GradeDao dao;

    @Autowired
    private SchoolDao schoolDao;

    @Test
    public void getGradeSchoolId() {
        System.out.println(dao.getGradeSchoolId(1702));
    }

    @Test
    public void getCollegeGrades() {
        System.out.println(dao.getCollegeGrades(1));
    }

    @Test
    public void addTasks() {
        dao.addTasks(1702,schoolDao.getCurrentTermId(1));
    }

    @Test
    public void isStuImportEnable() {
        System.out.println(dao.isLessonImportEnable(1702,19));
    }

    @Test
    public void isLessonImportEnable() {
        System.out.println(dao.isLessonImportEnable(1702,schoolDao.getCurrentTermId(dao.getGradeSchoolId(1702))));
    }
}